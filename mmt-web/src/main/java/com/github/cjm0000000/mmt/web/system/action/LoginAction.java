package com.github.cjm0000000.mmt.web.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.web.common.MMTAction;

/**
 * For login view
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public final class LoginAction extends MMTAction {
	/**
	 * verify user login
	 * @param user_name
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login")
	public ModelAndView login() {
		return info(null, null);
	}
	
	/**
	 * logout
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session){
		session.removeAttribute(TOKEN);
		session.removeAttribute(USER_CUSTOMIZATION_HOME);
		return new ModelAndView(VIEW_LOGOUT_PAGE,"msg","您已经成功退出。");
	}
	
	/**
	 * 拒绝访问
	 * @return
	 */
	@RequestMapping(value="/forbidden")
	public String forbidden(){
		return VIEW_FORBIDDEN;
	}
	
	/**
	 * 返回提示信息
	 * @param tip		提示信息
	 * @param user_name 给表单填充用户名
	 * @return
	 */
	private ModelAndView info(String tip, String user_name){
		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("username", user_name);
		msgMap.put("tip", tip);
		return new ModelAndView(VIEW_LOGIN_PAGE,"msg",msgMap);
	}
	
}
