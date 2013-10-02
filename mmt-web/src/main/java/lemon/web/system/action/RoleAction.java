package lemon.web.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.entity.Status;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	 * @param role
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public String save(Role role) {
		if(role == null)
			return "\u00ef\u00bb\u00bf\u00e4\u00bd\u00a0\u00e8\u00be\u0093\u00e5\u0085\u00a5\u00e7\u009a\u0084\u00e4\u00bf\u00a1\u00e6\u0081\u00af\u00e4\u00b8\u008d\u00e6\u00ad\u00a3\u00e7\u00a1\u00ae\u00e3\u0080\u0082";
		if(role.getRole_id() <= 0){
			role.setReloadable(Status.AVAILABLE);
			role.setStatus(Status.AVAILABLE);
			roleMapper.addRole(role);
		}else
			roleMapper.update(role);
		return "\u00ef\u00bb\u00bf\u00e4\u00bf\u009d\u00e5\u00ad\u0098\u00e6\u0088\u0090\u00e5\u008a\u009f\u00e3\u0080\u0082";
	}
	
	/**
	 * 删除角色
	 * @param role_id
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String role_id) {
		String[] ids = role_id.split(",");
		roleMapper.batchDelete(ids);
		return "\u00ef\u00bb\u00bf\u00e5\u0088\u00a0\u00e9\u0099\u00a4\u00e6\u0088\u0090\u00e5\u008a\u009f\u00e3\u0080\u0082";
	}
	
	/**
	 * 显示添加或者编辑角色的页面
	 * @param role_id
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(int role_id) {
		Role role = null;
		if (role_id != 0)
			role = roleMapper.getRole(role_id);
		if (role == null)
			role = new Role();
		return new ModelAndView(getAddEditView(), "role", role);
	}

	@Override
	protected String getMenuURL() {
		return "system/role";
	}

}
