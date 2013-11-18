package lemon.web.interfaces.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import lemon.shared.access.ReturnCode;
import lemon.shared.api.MmtAPI;
import lemon.shared.customer.CustomMenu;
import lemon.shared.customer.CustomMenuAdpater;
import lemon.shared.customer.mapper.CustomMenuMapper;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
import lemon.shared.toolkit.json.JSONHelper;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;
import lemon.weixin.WeiXinException;
import lemon.weixin.config.bean.AccountType;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;
import lemon.yixin.YiXinException;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.config.mapper.YXConfigMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@SessionAttributes(MMTAction.TOKEN)
public final class CustomMenuAction extends AdminNavAction {
	private static Log logger = LogFactory.getLog(CustomMenuAction.class);
	/** 虚拟根目录ID */
	private static final int VIRTUAL_ROOT_MENU_ID = -5743;
	private static final CustomMenu VIRTUAL_MENU = new CustomMenu();
	/** 自定义菜单类型 */
	private static final String MENU_TYPE_GROUP = "CUSTOM_MENU_TYPE";
	@Autowired
	private CustomMenuMapper customMenuMapper;
	@Autowired
	private WXConfigMapper wxConfigMapper;
	@Autowired
	private YXConfigMapper yxConfigMapper;
	@Resource(name="weiXinAPI")
	private MmtAPI weixinApi;
	@Resource(name="yiXinAPI")
	private MmtAPI yixinApi;
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	
	static{
		VIRTUAL_MENU.setMenu_id(VIRTUAL_ROOT_MENU_ID);
		VIRTUAL_MENU.setMenulevcod((byte) 0);
		VIRTUAL_MENU.setName("请选择上级菜单");
	}

	/**
	 * 显示菜单列表[无需分页]
	 * @param user
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute(TOKEN) User user) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		if(null == mainViewName)
			sendNotFoundError();
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
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@Valid CustomMenu menu, BindingResult br,
			@ModelAttribute(TOKEN) User user) {
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
			menu.setMenu_id(IdWorkerManager.getIdWorker(CustomMenu.class).getId());
			if(menu.getType().equals("click"))
				menu.setKey(generateKey(user.getCust_id()));
			result = customMenuMapper.addMenu(menu);
		}else{
			if(menu.getType().equals("click"))
				menu.setKey(null);
			result = customMenuMapper.editMenu(menu);
		}
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
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
	 * @param user
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(long menu_id, @ModelAttribute(TOKEN) User user) {
		// 查询上级菜单
		List<CustomMenu> pmList = customMenuMapper.getMenuListByLevel(user.getCust_id(), (byte) 1);
		pmList.add(0, VIRTUAL_MENU);
		// 查询当前菜单
		CustomMenu menu = null;
		if (menu_id > 0)
			menu = customMenuMapper.getMenu(menu_id);
		if(menu == null)
			menu = new CustomMenu();
		Map<String, Object> result = new HashMap<>(8);
		result.put("pmList", pmList);
		result.put("menu", menu);
		result.put("menuType", systemConfigMapper.getItems(MENU_TYPE_GROUP));
		return new ModelAndView(getAddEditView(), "result", result);
	}
	
	/**
	 * 同步自定义菜单到微信
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sync_menu_wx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String syncMenu2WX(@ModelAttribute(TOKEN) User user){
		WeiXinConfig cfg = wxConfigMapper.get(user.getCust_id());
		if(cfg == null)
			return sendJSONError("请先配置微信接口。");
		if(cfg.getAccount_type().equals(AccountType.DY))
			return sendJSONError("您是订阅号，无法同步自定义菜单，请先升级到服务号。");
		ReturnCode rCode = null;
		try{
			String json = generateJson(user.getCust_id());
			logger.debug(json);
			rCode = weixinApi.createMenus(cfg, json);
		}catch(WeiXinException e){
			return sendJSONError(e.getMessage());
		}
		if(rCode == null)
			return sendJSONError("自定义菜单同步失败。");
		//同步数据
		if(rCode.getErrcode() == 0)
			return sendJSONMsg("同步成功。");
		else
			return sendJSONError("同步失败：errcode=" + rCode.getErrcode()
					+ ", errmsg=" + rCode.getErrmsg());
	}
	
	/**
	 * 同步自定义菜单到易信
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sync_menu_yx", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String syncMenu2YX(@ModelAttribute(TOKEN) User user){
		YiXinConfig cfg = yxConfigMapper.get(user.getCust_id());
		if(cfg == null)
			return sendJSONError("请先配置易信接口。");
		ReturnCode rCode = null;
		try{
			String json = generateJson(user.getCust_id());
			logger.debug(json);
			rCode = yixinApi.createMenus(cfg, json);
		}catch(YiXinException e){
			return sendJSONError(e.getMessage());
		}
		if(rCode == null)
			return sendJSONError("自定义菜单同步失败。");
		//同步数据
		if(rCode.getErrcode() == 0)
			return sendJSONMsg("同步成功。");
		else
			return sendJSONError("同步失败：errcode=" + rCode.getErrcode()
					+ ", errmsg=" + rCode.getErrmsg());
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
			for (CustomMenu customMenu : l2_list)
				if (customMenu.getSupmenucode() == parent.getMenu_id())
					result.add(customMenu);
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
	 * 生成需要的JSON
	 * @param cust_id
	 * @return
	 */
	private String generateJson(int cust_id){
		//获取所有菜单
		List<CustomMenu> list = customMenuMapper.getMenuList(cust_id);
		//一级菜单
		List<CustomMenu> l1_list = getMenuListByLevel(list, (byte) 1);
		//二级菜单
		List<CustomMenu> l2_list = getMenuListByLevel(list, (byte) 2);
		list.clear();
		//生成最终结果
		List<CustomMenuAdpater> result = new ArrayList<>(list.size());
		String url;
		for (CustomMenu parent : l1_list) {
			//获取叶子节点
			List<CustomMenuAdpater> subList = new ArrayList<>();
			for (CustomMenu customMenu : l2_list) {
				if (customMenu.getSupmenucode() == parent.getMenu_id())
					if(customMenu.getType().equals("view"))
						subList.add(new CustomMenuAdpater(customMenu.getName(), customMenu.getType(), customMenu.getKey(), null, null));
					else
						subList.add(new CustomMenuAdpater(customMenu.getName(), customMenu.getType(), null, customMenu.getKey(), null));
			}
			if (subList.size() == 0)
				subList = null;
			//添加节点
			if(parent.getType().equals("view"))
				url = parent.getKey();
			else
				url = null;
			if(subList != null && subList.size() > 0){
				parent.setType(null);
				parent.setKey(null);
				url = null;
			}
			result.add(new CustomMenuAdpater(parent.getName(), parent.getType(), url, parent.getKey(), subList));
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
		for (CustomMenu customMenu : all) 
			if (customMenu.getMenulevcod() == level)
				result.add(customMenu);
		return result;
	}
	
}
