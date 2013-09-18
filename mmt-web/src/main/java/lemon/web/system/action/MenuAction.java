package lemon.web.system.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.base.MMTAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
//FIXME MENUAction 还没实现
/**
 * System configure index
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system")
public class MenuAction extends MMTAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
	 * show subsystem home page
	 * @param second
	 * @param third
	 * @param view
	 * @param session
	 * @return
	 */
	@RequestMapping("{second}/{third}/{view}")
	public ModelAndView index(@PathVariable String second,
			@PathVariable String third, @PathVariable String view,
			HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		Menu activeMenu = roleMenuMapper.getLeafMenuByUrl(third, second, user.getRole_id());
		if(null == activeMenu || !"3".equals(activeMenu.getMenulevcod()))
			sendNotFoundError();
		//获取站点名称
		List<Menu> root_list = roleMenuMapper.getMenuListByRole(user.getRole_id(), "1");
		if(root_list.size() == 0){
			sendNotFoundError();
		}
		Menu root = root_list.get(0);
		//获取导航菜单
		List<Menu> nav_list = roleMenuMapper.getMenuListByRole(user.getRole_id(), "2");
		//获取左侧导航菜单
		int activeNav = activeMenu.getSupmenucode();
		List<Menu> left_nav_list = roleMenuMapper.getLeafMenuListByRole(user.getRole_id(), activeNav);
		//获取面包削导航
		Menu secondMenu = roleMenuMapper.getSecondLevelMenuByUrl(second, user.getRole_id());
		Map<String, Menu> breadNavMap = new HashMap<>();
		breadNavMap.put("second", secondMenu);
		breadNavMap.put("third", activeMenu);
		//获取Main页面数据
		//传给模板
		Map<String, Object> index = new HashMap<>();
		index.put("nav_list", nav_list);
		index.put("left_nav_list", left_nav_list);
		index.put("site_name", root.getMenu_name());
		index.put(USER_CUSTOMIZATION_HOME, activeMenu.getMenu_id());
		index.put("active-nav", activeNav);
		index.put("breadcrumb-nav", breadNavMap);
		return new ModelAndView(VIEW_SYSTEM_HOME_PAGE, "index", index);
	}
	
	/**
	 * show subsystem home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("{second}")
	public String index(@PathVariable String second, HttpSession session) {
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
	@RequestMapping("{second}/{third}")
	public String index(@PathVariable String second,@PathVariable String third, HttpSession session) {
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
}
