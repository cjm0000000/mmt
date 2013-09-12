package lemon.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lemon.web.HomeAction;
import lemon.web.system.action.LoginAction;
import lemon.web.system.bean.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * MMT Web HandlerInterceptor
 * @author lemon
 * @version 1.0
 *
 */
public class MMTHandlerInterceptor implements HandlerInterceptor {
	private static Log logger = LogFactory.getLog(MMTHandlerInterceptor.class);
	
	@Override
	/*
	 * Action执行之前执行
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//对于主页和登录页面不做处理
		if(handler instanceof LoginAction || handler instanceof HomeAction)
			return true;
		User user = (User) request.getSession().getAttribute("user");
		//跳到登录页面
		if(null == user){
			logger.debug("用户SESSION失效");
			response.sendRedirect("/index");
			return false;
		}
		return true;
	}

	@Override
	/*
	 * Action 执行之后,生成视图之前执行
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("After Action, before view");
	}

	@Override
	/*
	 * 最后执行，用于释放资源
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("RELEASE...");
	}

}
