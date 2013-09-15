package lemon.web.system.action;


import java.util.List;

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
	 * 
	 * @return
	 */
	@RequestMapping("system/{menu_id}")
	public ModelAndView index(@PathVariable int menu_id, HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		//获取导航菜单
		List<Menu> nav_list = menuMapper.getMenuListByLevel("2");
		for (Menu menu : nav_list) {
			System.out.println(menu.getMenu_name());
		}
		return new ModelAndView(VIEW_SYSTEM_HOME_PAGE, "nav_list", nav_list);
	}
}
