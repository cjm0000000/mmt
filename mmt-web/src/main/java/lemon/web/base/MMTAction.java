package lemon.web.base;

/**
 * MMT base action
 * @author lemon
 * @version 1.0
 *
 */
public class MMTAction {
	/** 首页视图 */
	protected static final String VIEW_HOME_PAGE = "redirect:/index";
	/** 登录页面视图 */
	protected static final String VIEW_LOGIN_PAGE = "/login";
	/** 登出页面视图 */
	protected static final String VIEW_LOGOUT_PAGE = "/logout";
	//FIXME 封装Action：分页，错误信息，提示信息，跳转路径等
	
	public static final String TOKEN = "user_token";
	//FIXME 设置好角色，显示菜单，配置权限等
}
