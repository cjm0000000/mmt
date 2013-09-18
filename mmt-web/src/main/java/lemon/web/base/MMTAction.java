package lemon.web.base;

import org.springframework.web.servlet.ModelAndView;

import lemon.web.global.MMTException;

/**
 * MMT base action
 * @author lemon
 * @version 1.0
 *
 */
public class MMTAction {
	/** 首页视图 */
	protected static final String VIEW_HOME_PAGE = "index";
	/** 系统首页视图 */
	protected static final String VIEW_SYSTEM_HOME_PAGE = "system/index";
	/** 登录页面视图 */
	protected static final String VIEW_LOGIN_PAGE = "/login";
	/** 登出页面视图 */
	protected static final String VIEW_LOGOUT_PAGE = "/logout";
	/** 存放用户Session */
	public static final String TOKEN = "user_token";
	/** 用户定制的首页 */
	public static final String USER_CUSTOMIZATION_HOME = "CUSTOMIZATION_HOME";
	/** 默认视图 */
	public static final String DEFAULT_VIEW = "list";
	/** 无权限视图 */
	public static final String VIEW_FORBIDDEN = "forbidden";
	
	/**
	 * 资源不存在，转发到错误页面
	 */
	protected void sendNotFoundError(){
		sendError("您访问的资源不存在。");
	}
	
	/**
	 * 发送错误消息
	 * @param error
	 */
	protected void sendError(String error){
		throw new MMTException(error);
	}
	
	/**
	 * 发送提示信息
	 * @param msg
	 * @return
	 */
	protected ModelAndView sendMsg(String msg){
		return new ModelAndView("msg","msg",msg);
	}
}
