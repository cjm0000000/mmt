package lemon.web.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import lemon.web.base.MMTAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.mapper.RoleMenuMapper;

/**
 * 管理员导航条
 * @author lemon
 * @version 1.0
 *
 */
public class AdminNavAction extends MMTAction {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	/**
	 * 获取带导航数据的视图<br>
	 * @param role_id
	 * @param thirdMenu
	 * @return
	 */
	protected ModelAndView buildNav(int role_id, Menu thirdMenu) {
		// FIXME buildNav的数据处理；传menu_id的方法改成传thirdMenu对象
		Map<String, Object> page = new HashMap<>();
		page.put("nav_list", getTopNavBar(role_id));
		page.put("left_nav_list",
				getLeftNavBar(role_id, thirdMenu.getSupmenucode()));
		page.put("site_name", getSiteName(role_id));
		page.put("active-nav", thirdMenu);
		page.put("breadcrumb-nav",
				getBreadCrumbNavBar(role_id, thirdMenu.getMenu_id()));
		return null;
	}
	
	/**
	 * 获取顶部导航栏
	 * @param role_id
	 * @return
	 */
	private List<Menu> getTopNavBar(int role_id){
		return roleMenuMapper.getMenuListByRole(role_id, "2");
	}
	
	/**
	 * 获取左侧导航栏
	 * @param role_id
	 * @param superMenuId
	 * @return
	 */
	private List<Menu> getLeftNavBar(int role_id, int superMenuId){
		return roleMenuMapper.getLeafMenuListByRole(role_id, superMenuId);
	}
	
	/**
	 * 获取面包削导航栏
	 * @param role_id
	 * @param menu_id
	 * @return
	 */
	private Map<String, Menu> getBreadCrumbNavBar(int role_id, int menu_id){
		Menu thirdMenu = roleMenuMapper.getMenuByRoleAndId(role_id, menu_id);
		Menu secondMenu = roleMenuMapper.getMenuByRoleAndId(role_id, thirdMenu.getSupmenucode());
		Map<String, Menu> breadNavMap = new HashMap<>(4);
		breadNavMap.put("second", secondMenu);
		breadNavMap.put("third", thirdMenu);
		return breadNavMap;
	}
	
	/**
	 * 获取站点名称
	 * @param role_id
	 * @return
	 */
	private String getSiteName(int role_id) {
		List<Menu> root_list = roleMenuMapper.getMenuListByRole(role_id, "1");
		if (root_list.size() == 0)
			sendNotFoundError();
		return root_list.get(0).getMenu_name();
	}
	
}
