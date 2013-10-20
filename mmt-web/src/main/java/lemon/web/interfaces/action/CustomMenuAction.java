package lemon.web.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lemon.shared.api.MmtAPI;
import lemon.shared.customer.bean.CustomMenu;
import lemon.shared.customer.mapper.CustomMenuMapper;
import lemon.shared.request.bean.ReturnCode;
import lemon.shared.toolkit.json.JSONHelper;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;
import lemon.weixin.config.bean.AccountType;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;
import lemon.weixin.custommenu.bean.WXCustomMenuAdpater;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	@Autowired
	private WXConfigMapper wxConfigMapper;
	@Resource(name="weiXinAPI")
	private MmtAPI weixinApi;
	@Resource(name="yiXinAPI")
	private MmtAPI yixinApi;
	
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
	//FIXME 如果是VIEW类型，需要输入网址
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
	
	/**
	 * 同步自定义菜单到微信
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "sync_menu_wx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String syncMenu2WX(HttpSession session){
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		WeiXinConfig cfg = wxConfigMapper.get(user.getCust_id());
		if(cfg == null)
			return sendJSONError("请先配置微信接口。");
		if(cfg.getAccount_type().equals(AccountType.DY))
			return sendJSONError("您是订阅号，无法同步自定义菜单，请先升级到服务号。");
		//同步数据
		ReturnCode rCode = weixinApi.createMenus(cfg, generateWXJson(user.getCust_id()));
		if(rCode.getErrcode() == 0)
			return sendJSONMsg("同步成功。");
		else
			return sendJSONError("同步失败：errcode=" + rCode.getErrcode()
					+ ", errmsg=" + rCode.getErrmsg());
	}
	
	/**
	 * 同步自定义菜单到易信
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "sync_menu_yx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String syncMenu2YX(HttpSession session){
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
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
		// 一级菜单
		List<CustomMenu> l1_list = getMenuListByLevel(list, (byte) 1);
		// 二级菜单
		List<CustomMenu> l2_list = getMenuListByLevel(list, (byte) 2);
		list.clear();
		//生成最终结果
		List<CustomMenu> result = new ArrayList<>(list.size());
		for (CustomMenu parent : l1_list) {
			result.add(parent);
			for (CustomMenu customMenu : l2_list) {
				if (customMenu.getSupmenucode() == parent.getMenu_id())
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
	
	/**
	 * 生成微信需要的JSON
	 * @param cust_id
	 * @return
	 */
	private String generateWXJson(int cust_id){
		//获取所有菜单
		List<CustomMenu> list = customMenuMapper.getMenuList(cust_id);
		//一级菜单
		List<CustomMenu> l1_list = getMenuListByLevel(list, (byte) 1);
		//二级菜单
		List<CustomMenu> l2_list = getMenuListByLevel(list, (byte) 2);
		list.clear();
		//生成最终结果
		List<WXCustomMenuAdpater> result = new ArrayList<>(list.size());
		for (CustomMenu parent : l1_list) {
			//获取叶子节点
			List<WXCustomMenuAdpater> subList = new ArrayList<>();
			for (CustomMenu customMenu : l2_list) {
				if (customMenu.getSupmenucode() == parent.getMenu_id())
					subList.add(new WXCustomMenuAdpater(customMenu.getName(), customMenu.getType(), customMenu.getKey(), customMenu.getKey(), null));
			}
			if (subList.size() == 0)
				subList = null;
			//添加节点
			result.add(new WXCustomMenuAdpater(parent.getName(), parent.getType(), parent.getKey(), parent.getKey(), subList));
		}
		l1_list.clear();
		l2_list.clear();

		// 生成JSON
		JSONArray jsonArray = JSONArray.fromObject(result, JSONHelper.filterNull());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("button", jsonArray);
		result.clear();
		return jsonObj.toString();
	}
	
	/**
	 * 根据菜单等级获取菜单列表
	 * @param all
	 * @param level
	 * @return
	 */
	private List<CustomMenu> getMenuListByLevel(List<CustomMenu> all, byte level) {
		List<CustomMenu> result = new ArrayList<>(all.size());
		for (CustomMenu customMenu : all) {
			if (customMenu.getMenulevcod() == level)
				result.add(customMenu);
		}
		return result;
	}
	
}
