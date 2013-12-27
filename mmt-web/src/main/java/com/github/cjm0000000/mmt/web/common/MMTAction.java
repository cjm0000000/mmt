package com.github.cjm0000000.mmt.web.common;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.cjm0000000.mmt.web.WebException;
import com.github.cjm0000000.mmt.web.global.JSONResult;
import com.github.cjm0000000.mmt.web.ui.BS3UI;

/**
 * MMT base action
 * @author lemon
 * @version 1.0
 *
 */
public abstract class MMTAction {
	/** 首页视图 */
	protected static final String VIEW_HOME_PAGE = "index";
	/** 后台管理首页视图 */
	protected static final String VIEW_MANAGE_HOME_PAGE = "manage/index";
	/** 登录页面视图 */
	protected static final String VIEW_LOGIN_PAGE = "/login";
	/** 登出页面视图 */
	protected static final String VIEW_LOGOUT_PAGE = "/logout";
	/** 无权限视图 */
	protected static final String VIEW_FORBIDDEN = "/forbidden";
	/** 存放用户Session */
	public static final String TOKEN = "user_token";
	/** 用户定制的首页 */
	public static final String USER_CUSTOMIZATION_HOME = "CUSTOMIZATION_HOME";
	/** 默认视图 */
	protected static final String DEFAULT_VIEW = "list";
	/** 用户存放密钥的KEY */
	public static final String ENCRYPY_KEY = "EncryptKey";
	/** 存放域名的KEY */
	protected static final String DOMAIN_KEY = "DOMAIN";
	
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
		throw new WebException(error);
	}
	
	/**
	 * 发送提示信息
	 * @param msg
	 * @return
	 */
	protected ModelAndView sendMsg(String msg){
		return new ModelAndView("msg","msg",msg);
	}
	
	/**
	 * 发送JSON提示消息
	 * @param msg
	 * @return
	 */
	protected String sendJSONMsg(String msg){
		return prepareJSON(true, BS3UI.success(msg));
	}
	
	/**
	 * 发送JSON错误消息
	 * @param error
	 * @return
	 */
	protected String sendJSONError(String error){
		return prepareJSON(false, BS3UI.danger(error));
	}
	
	/**
	 * parse to JSON string
	 * @param success
	 * @param message
	 * @return
	 */
	private String prepareJSON(boolean success, String message){
		return JSON.toJSONString(new JSONResult(success, message));
	}
	
}
