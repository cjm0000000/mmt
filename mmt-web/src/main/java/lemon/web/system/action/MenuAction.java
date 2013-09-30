package lemon.web.system.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.MenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public final class MenuAction extends AdminNavAction {
	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 显示菜单列表[无需分页]
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpSession session) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		if(null == mainViewName)
			sendNotFoundError();
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		List<Menu> menuList = obtainMenuTree();
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("menuList", menuList);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	
	/**
	 * 添加菜单
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(HttpSession session) {
		//TODO 添加菜单
		return "";
	}
	
	/**
	 * 删除菜单
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete( HttpSession session) {
		//TODO 删除菜单
		return "";
	}
	
	/**
	 * 编辑菜单
	 * @param session
	 * @return
	 */
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(HttpSession session) {
		//TODO 编辑菜单
		return "";
	}
	
	/**
	 * 显示添加或者编辑菜单的页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public String addOrEditPage(HttpSession session) {
		//TODO 显示添加或者编辑菜单的页面
		return getAddEditView();
	}

	@Override
	protected String getMenuURL() {
		return "system/menu";
	}
	
	/**
	 * 生成菜单树
	 * @return
	 */
	private List<Menu> obtainMenuTree(){
		//获取二级菜单
		List<Menu> l2_list = menuMapper.getMenuListByLevel("2");
		//三级菜单
		List<Menu> l3_list = null;
		//结果
		List<Menu> result = new ArrayList<>();
		for (Menu parent : l2_list) {
			result.add(parent);
			l3_list = menuMapper.getMenuListByParent(parent.getMenu_id());
			if(l3_list == null || l3_list.size() == 0)
				continue;
			for (Menu l3 : l3_list) {
				result.add(l3);
			}
		}
		l2_list.clear();
		l3_list.clear();
		return result;
	}

}
