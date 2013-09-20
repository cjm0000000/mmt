package lemon.web.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lemon.web.base.MMTAction;
import lemon.web.system.bean.Menu;
import lemon.web.system.mapper.MenuMapper;
import lemon.web.system.mapper.RoleMenuMapper;

/**
 * 管理员导航条
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AdminNavAction extends MMTAction {
	/** 新增内容视图 */
	protected static final String VIEW_ADD = "add";
	/** 编辑内容视图 */
	protected static final String VIEW_EDIT = "edit";
	/** 删除内容视图 */
	protected static final String VIEW_DELETE = "edit";
	/** 默认每页数量 */
	protected static final int PAGESIZE = 10;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private MenuMapper menuMapper;
	
	/**
	 * 获取菜单URL
	 * @return
	 */
	protected abstract String getMenuURL();
	
	/**
	 * 获取带导航数据的视图
	 * @param role_id
	 * @param menuurl
	 * @return
	 */
	protected Map<String, Object> buildNav(int role_id) {
		Menu thirdMenu = getActiveMenu(role_id, getMenuURL());
		if(null == thirdMenu)
			sendNotFoundError();
		Map<String, Object> page = new HashMap<>();
		page.put("top-nav", getTopNavBar(role_id));
		page.put("left-nav", getLeftNavBar(role_id, thirdMenu.getSupmenucode()));
		page.put("site_name", getSiteName(role_id));
		page.put("active-nav", thirdMenu);
		page.put("breadcrumb-nav", getBreadCrumbNavBar(role_id, thirdMenu));
		return page;
	}
	
	/**
	 * 获取Main视图名称
	 * @param operation 当前操作名称
	 * @return
	 */
	protected String getMainViewName(String operation) {
		String[] temp = getMenuURL().split("/");
		if (temp.length != 2)
			return null;
		return temp[0] + "/" + temp[1] + "_" + operation;
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
	private Map<String, Menu> getBreadCrumbNavBar(int role_id, Menu thirdMenu){
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
	
	/**
	 * 根据角色和URL获取当前选中的菜单
	 * @param role_id
	 * @param url
	 * @return
	 */
	private Menu getActiveMenu(int role_id, String url){
		return roleMenuMapper.getMenuByRoleAndUrl(role_id, url);
	}
	
}
