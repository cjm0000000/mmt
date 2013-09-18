package lemon.web.system.action;

import javax.servlet.http.HttpSession;

import lemon.web.base.MMTAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * System index, for redirect
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
public class SystemIndexAction extends MMTAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	/**
	 * show system home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("system/{second}")
	public String index(@PathVariable String second, HttpSession session) {
		if(null == second || "".equals(second))
			sendNotFoundError();
		User user = (User) session.getAttribute(TOKEN);
		Menu activeMenu = roleMenuMapper.getMenuByRoleAndUrl(user.getRole_id(), "system/"+second);
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:../" + activeMenu.getMenuurl() + "/"
				+ DEFAULT_VIEW;
		return view;
	}
	
	/**
	 * show system home page
	 * @param session
	 * @return
	 */
	@RequestMapping("system")
	public String index(HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		//获取二级目录
		Menu secondMenu = roleMenuMapper.getMenuByRoleAndUrl(user.getRole_id(), "system");
		if(null == secondMenu)
			sendNotFoundError();
		//获取三级目录
		Menu activeMenu = roleMenuMapper.getDefaultChild(secondMenu.getMenu_id(), user.getRole_id());
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:../" + activeMenu.getMenuurl() + "/"
				+ DEFAULT_VIEW;
		return view;
	}

}
