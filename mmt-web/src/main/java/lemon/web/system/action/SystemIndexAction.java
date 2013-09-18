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
//FIXME 撤销这个类,采用业务具体实现的类
/**
 * System configure index
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system")
public class SystemIndexAction extends MMTAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
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
		String view = "redirect:"
				+ activeMenu.getMenuurl() + "/" + DEFAULT_VIEW;
		return view;
	}

}
