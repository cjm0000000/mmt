package lemon.web.system.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.base.MMTAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.MenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * System configure index
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
public class SystemIndexAction extends MMTAction {
	@Autowired
	private MenuMapper menuMapper;

	/**
	 * show system home page
	 * @param nav_menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping("system/{cust_home_menu}")
	public ModelAndView index(@PathVariable int cust_home_menu, HttpSession session) {
		Menu activeMenu = menuMapper.getMenu(cust_home_menu);
		if(null == activeMenu || !"3".equals(activeMenu.getMenulevcod())){
			//FIXME Spring Exception处理，参考github 例子
			throw new RuntimeException();
		}
		User user = (User) session.getAttribute(TOKEN);
		//获取站点名称
		List<Menu> root_list = menuMapper.getMenuListByLevel("1");
		if(root_list.size() == 0){
			//FIXME Spring Exception处理，参考github 例子
			throw new RuntimeException();
		}
		Menu root = root_list.get(0);
		//获取导航菜单
		List<Menu> nav_list = menuMapper.getMenuListByRole(user.getRole_id(), "2");
		//获取左侧导航菜单
		int activeNav = activeMenu.getSupmenucode();
		List<Menu> left_nav_list = menuMapper.getLeafMenuListByRole(user.getRole_id(), activeNav);
		//传给模板
		Map<String, Object> index = new HashMap<>();
		index.put("nav_list", nav_list);
		index.put("left_nav_list", left_nav_list);
		index.put("site_name", root.getMenu_name());
		index.put(USER_CUSTOMIZATION_HOME, cust_home_menu);
		index.put("active-nav", activeNav);
		return new ModelAndView(VIEW_SYSTEM_HOME_PAGE, "index", index);
	}
}
