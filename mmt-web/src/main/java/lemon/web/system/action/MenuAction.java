package lemon.web.system.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统菜单管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system/menu")
public class MenuAction extends AdminNavAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
	 * 显示菜单列表[无需分页]
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpSession session) {
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		String main = "";
		resultMap.put("main", main);
		ModelAndView  mv = new ModelAndView(VIEW_SYSTEM_HOME_PAGE, "page", resultMap);
		return mv;
	}
	
	/**
	 * show subsystem home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("add")
	public String add(@PathVariable String second, HttpSession session) {
		if(null == second || "".equals(second))
			sendNotFoundError();
		User user = (User) session.getAttribute(TOKEN);
		//查询二级目录，如果不存在，跳转到错误页面
		Menu superMenu = roleMenuMapper.getSecondLevelMenuByUrl(second, user.getRole_id());
		if(null == superMenu)
			sendNotFoundError();
		//查询三级目录，如果不存在，跳转到错误页面
		Menu activeMenu = roleMenuMapper.getDefaultChild(superMenu.getMenu_id(), user.getRole_id());
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:" + superMenu.getMenuurl() + "/"
				+ activeMenu.getMenuurl() + "/" + DEFAULT_VIEW;
		return view;
	}
	
	/**
	 * show subsystem home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("delete")
	public String delete(@PathVariable String second,@PathVariable String third, HttpSession session) {
		if (null == second || null == third || "".equals(second)
				|| "".equals(third))
			sendNotFoundError();
		User user = (User) session.getAttribute(TOKEN);
		Menu activeMenu = roleMenuMapper.getLeafMenuByUrl(third, second, user.getUser_id());
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:" + third + "/" + DEFAULT_VIEW;
		return view;
	}

	@Override
	protected String getMenuURL() {
		return "system/menu";
	}
}
