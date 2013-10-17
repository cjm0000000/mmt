package lemon.web.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lemon.shared.customer.bean.CustomMenu;
import lemon.shared.customer.mapper.CustomMenuMapper;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义菜单管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/interface/menu")
public final class CustomMenuAction extends AdminNavAction {
	/** 虚拟根目录ID */
	private static final int VIRTUAL_ROOT_MENU_ID = -5743;
	private static final List<String> MENU_TYPE = new ArrayList<>(2);
	private static final CustomMenu VIRTUAL_MENU = new CustomMenu();
	@Autowired
	private CustomMenuMapper customMenuMapper;
	
	static{
		MENU_TYPE.add("click");
		MENU_TYPE.add("view");
		VIRTUAL_MENU.setMenu_id(VIRTUAL_ROOT_MENU_ID);
		VIRTUAL_MENU.setMenulevcod((byte) 0);
		VIRTUAL_MENU.setName("请选择上级菜单");
	}

	/**
	 * 显示菜单列表[无需分页]
	 * @param session
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
		List<CustomMenu> menuList = obtainMenuTree(user.getCust_id());
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("menuList", menuList);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	
	/**
	 * 保存菜单
	 * @param menu
	 * @param br
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(@Valid CustomMenu menu, BindingResult br,
			HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		CustomMenu supMenu;
		if(menu.getSupmenucode() == VIRTUAL_ROOT_MENU_ID)
			supMenu = VIRTUAL_MENU;
		else
			supMenu = customMenuMapper.getMenu(menu.getSupmenucode());
		if(supMenu == null)
			return sendJSONError("保存失败： 上级菜单不存在。");
		if(br.hasErrors())
			return sendJSONError(br.getFieldError().getDefaultMessage());
		byte lev = supMenu.getMenulevcod();
		menu.setMenulevcod(++lev);
		menu.setCust_id(user.getCust_id());
		int result = 0;
		if(menu.getMenu_id() <= 0){
			menu.setKey(generateKey(user.getCust_id()));
			result = customMenuMapper.addMenu(menu);
		}else
			result = customMenuMapper.editMenu(menu);
		if(result == 0)
			return sendJSONError("菜单保存失败。");
		else
			return sendJSONMsg("菜单保存成功。");
	}
	
	/**
	 * 删除菜单
	 * @param menu_id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(String menu_id) {
		String[] ids = menu_id.split(",");
		int result = customMenuMapper.deleteMenu(ids);
		if (result == 0)
			return sendJSONError("菜单删除失败。");
		else
			return sendJSONMsg("菜单删除成功。");
	}
	
	/**
	 * 显示添加或者编辑菜单的页面
	 * @param menu_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(int menu_id, HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		// 查询上级菜单
		List<CustomMenu> pmList = customMenuMapper.getMenuListByLevel(user.getCust_id(), (byte) 1);
		pmList.add(0, VIRTUAL_MENU);
		// 查询当前菜单
		CustomMenu menu = null;
		if (menu_id > 0)
			menu = customMenuMapper.getMenu(menu_id);
		if(menu == null)
			menu = new CustomMenu();
		Map<String, Object> result = new HashMap<>();
		result.put("pmList", pmList);
		result.put("menu", menu);
		result.put("menuType", MENU_TYPE);
		return new ModelAndView(getAddEditView(), "result", result);
	}
	
	@RequestMapping(value = "sync_menu_wx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String syncMenu2WX(HttpSession session){
		//FIXME 把自定义菜单同步到微信
		return null;
	}
	
	@RequestMapping(value = "sync_menu_yx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String syncMenu2YX(HttpSession session){
		//FIXME 把自定义菜单同步到易信
		return null;
	}

	@Override
	protected String getMenuURL() {
		return "interface/menu";
	}
	
	@Override
	protected int lastPage(int currentPage, int rsCnt) {
		return 1;
	}
	
	/**
	 * 生成菜单树
	 * @param cust_id
	 * @return
	 */
	private List<CustomMenu> obtainMenuTree(int cust_id){
		//获取所有菜单
		List<CustomMenu> list = customMenuMapper.getMenuList(cust_id);
		//一级菜单
		List<CustomMenu> l1_list = new ArrayList<>(list.size());
		//二级菜单
		List<CustomMenu> l2_list = new ArrayList<>(list.size());
		//分离菜单
		for (CustomMenu customMenu : list) {
			if(customMenu.getMenulevcod() == 1)
				l1_list.add(customMenu);
			else if(customMenu.getMenulevcod() == 2)
				l2_list.add(customMenu);
		}
		list.clear();
		
		//生成最终结果
		List<CustomMenu> result = new ArrayList<>(list.size());
		for (CustomMenu parent : l1_list) {
			result.add(parent);
			for (CustomMenu customMenu : l2_list) {
				if(customMenu.getSupmenucode() == parent.getMenu_id())
					result.add(customMenu);
			}
		}
		l1_list.clear();
		l2_list.clear();
		return result;
	}
	
	/**
	 * 生成每个客户唯一的KEY
	 * @param cust_id
	 * @return
	 */
	private String generateKey(int cust_id){
		String key = UUID.randomUUID().toString();
		while(true){
			if(customMenuMapper.getMenuByKey(cust_id, key) == null)
				return key;
			else key = UUID.randomUUID().toString();
		}
	}
	
}
