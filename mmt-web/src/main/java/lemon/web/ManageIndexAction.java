package lemon.web;

import lemon.web.base.MMTAction;
import lemon.web.global.MMT;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
/**
 * Manage index page, for redirect
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@SessionAttributes(MMTAction.TOKEN)
public class ManageIndexAction extends MMTAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	/**
	 * show manager home page
	 * @param first
	 * @param second
	 * @param user
	 * @return
	 */
	@RequestMapping("{first}/{second}")
	public String index(@PathVariable String first,
			@PathVariable String second, @ModelAttribute(TOKEN) User user) {
		if(null == second || "".equals(second))
			sendNotFoundError();
		Menu activeMenu = roleMenuMapper.getMenuByRoleAndUrl(user.getRole_id(),
				first + "/" + second);
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:" + getManageRoot() + activeMenu.getMenuurl()
				+ "/" + DEFAULT_VIEW;
		return view;
	}
	
	/**
	 * show manage home page
	 * @param menu_L2 二级菜单
	 * @param user
	 * @return
	 */
	@RequestMapping("{menu_L2}")
	public String index(@PathVariable String menu_L2, @ModelAttribute(TOKEN) User user) {
		//获取二级目录
		Menu secondMenu = roleMenuMapper.getMenuByRoleAndUrl(user.getRole_id(), menu_L2);
		if(null == secondMenu)
			sendNotFoundError();
		//获取三级目录
		Menu activeMenu = roleMenuMapper.getDefaultChild(secondMenu.getMenu_id(), user.getRole_id());
		if(null == activeMenu)
			sendNotFoundError();
		//跳转到视图
		String view = "redirect:" + getManageRoot() + activeMenu.getMenuurl()
				+ "/" + DEFAULT_VIEW;
		return view;
	}
	
	/**
	 * 获取Manage根路径
	 * @return
	 */
	private String getManageRoot(){
		return MMT.FILTER_ROOT;
	}

}
