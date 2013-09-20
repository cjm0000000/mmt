package lemon.web.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 角色管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system/role")
public final class RoleAction extends AdminNavAction {
	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 显示角色列表首页
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:list/1";
	}
	
	/**
	 * 分页显示角色列表
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable("page") int page, HttpSession session) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		if(null == mainViewName)
			sendNotFoundError();
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		List<Role> roleList = roleMapper.getRoleList((page - 1) * PAGESIZE,
				PAGESIZE);
		System.out.println("roleList sze;"+roleList.size());
		int rsCnt = roleMapper.getRsCnt();
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("roleList", roleList);
		resultMap.put("rsCnt", rsCnt);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	
	/**
	 * 添加角色
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String add(HttpSession session) {
		//TODO 添加角色
		return "";
	}
	
	/**
	 * 删除角色
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public String delete(HttpSession session) {
		//TODO 删除角色
		return "";
	}
	
	/**
	 * 编辑角色
	 * @param session
	 * @return
	 */
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(HttpSession session) {
		//TODO 编辑角色
		return "";
	}
	
	/**
	 * 显示添加或者编辑角色的页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public String addOrEditPage(HttpSession session) {
		//TODO 显示添加或者编辑角色的页面
		return VIEW_ADD_EDIT;
	}

	@Override
	protected String getMenuURL() {
		return "system/role";
	}

}
