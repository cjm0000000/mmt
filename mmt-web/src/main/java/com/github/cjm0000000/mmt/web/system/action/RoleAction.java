package com.github.cjm0000000.mmt.web.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.web.WebException;
import com.github.cjm0000000.mmt.web.common.AdminNavAction;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.common.paging.Pagination;
import com.github.cjm0000000.mmt.web.security.MMTSecurityMetadataSource;
import com.github.cjm0000000.mmt.web.system.bean.Menu;
import com.github.cjm0000000.mmt.web.system.bean.Role;
import com.github.cjm0000000.mmt.web.system.bean.User;
import com.github.cjm0000000.mmt.web.system.persistence.RoleRepository;
/**
 * 角色管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system/role")
@SessionAttributes(MMTAction.TOKEN)
public class RoleAction extends AdminNavAction {
	@Autowired
	private RoleRepository roleMapper;
	@Autowired
	private MMTSecurityMetadataSource securityMetadataSource;

	/**
	 * 显示角色列表首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:/webservices/system/role/list/1";
	}
	
	/**
	 * 分页显示角色列表
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list/{page}",method=RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page, ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取Main视图名称
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<Role> roleList = roleMapper.getRoleList((page - 1) * PAGESIZE, PAGESIZE);
		Pagination pg = new Pagination(page, PAGESIZE, roleMapper.getRsCnt());
		return getListResultByPagination(pg, user.getRole_id(), operation, roleList);
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @param br
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@Valid Role role, BindingResult br) {
		if(role == null)
			return sendJSONError("添加失败 ：  角色信息不全。");
		if(br.hasErrors())
			return sendJSONError(br.getFieldError().getDefaultMessage());
		int result = 0;
		if(role.getRole_id() <= 0){
			role.setReloadable(Status.AVAILABLE);
			role.setStatus(Status.AVAILABLE);
			result = roleMapper.addRole(role);
		}else
			result = roleMapper.update(role);
		if(result == 0)
			return sendJSONError("角色信息保存失败。");
		return sendJSONMsg("角色信息保存成功。");
	}
	
	/**
	 * 设置权限
	 * @param role_id
	 * @param menu_id
	 * @return
	 */
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value="set-authority", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String setAuthority(int role_id, String menu_id){
		if (role_id <= 0 || menu_id == null)
			return sendJSONError("角色权限设置失败。");
		int result = roleMapper.deleteRoleAuthority(role_id);
		if (!"".equals(menu_id)) {
			String[] menus = menu_id.split(",");
			result = result | roleMapper.setRoleAuthority(role_id, menus);
		}
		if (result == 0)
			return sendJSONError("角色权限设置失败。");
		securityMetadataSource.loadResourceDefine();
		return sendJSONMsg("角色权限设置成功。");
	}
	
	/**
	 * 删除角色
	 * @param role_id
	 * @return
	 */
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value="delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String delete(String role_id) {
		String[] ids = role_id.split(",");
		int result = roleMapper.batchDelete(ids);
		try {
			for (String role : ids)
				roleMapper.deleteRoleAuthority(Integer.parseInt(role));
		} catch (NumberFormatException e) {
			return sendJSONError("角色信息删除失败。");
		}
		if(result == 0)
			return sendJSONError("角色信息删除失败。");
		return sendJSONMsg("角色信息删除成功。");
	}
	
	/**
	 * 显示添加或者编辑角色的页面
	 * @param role_id
	 * @return
	 */
	@RequestMapping(value = "add-edit-page", method = RequestMethod.GET)
	public ModelAndView addOrEditPage(int role_id) {
		Role role = null;
		if (role_id != 0)
			role = roleMapper.getRole(role_id);
		if (role == null)
			role = new Role();
		return new ModelAndView(getAddEditView(), "role", role);
	}
	
	/**
	 * 显示角色权限列表
	 * @param role_id
	 * @return
	 */
	@RequestMapping(value = "authority-list-page", method = RequestMethod.GET)
	public ModelAndView authorityListPage(int role_id) {
		if(role_id == 0)
			throw new WebException("角色不存在。");
		List<Menu> list = roleMapper.getAuthority(role_id);
		list = obtainAuthorityTree(list);
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("role_id", role_id);
		return new ModelAndView("manage/system/role-authority-list", "result", result);
	}

	@Override
	protected String getMenuURL() {
		return "system/role";
	}
	
	/**
	 * obtain authority tree original list
	 * @param original
	 * @return
	 */
	private List<Menu> obtainAuthorityTree(List<Menu> original){
		if(original == null || original.size() == 0)
			return null;
		List<Menu> l2_list = new ArrayList<>(original.size());
		List<Menu> l3_list = new ArrayList<>(original.size());
		
		for (Menu menu : original) {
			if(menu.getMenulevcod().equals("2"))
				l2_list.add(menu);
			if(menu.getMenulevcod().equals("3"))
				l3_list.add(menu);
		}
		
		//结果集
		List<Menu> result = new ArrayList<>();
		for (Menu m2 : l2_list) {
			//添加二级菜单
			result.add(m2);
			//添加三级菜单
			for (Menu m3 : l3_list)
				if(m3.getSupmenucode() == m2.getMenu_id())
					result.add(m3);
		}
		l2_list.clear();
		l3_list.clear();
		return result;
	}
	
}
