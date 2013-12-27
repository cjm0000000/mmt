package com.github.cjm0000000.mmt.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.shared.toolkit.secure.SecureUtil;
import com.github.cjm0000000.mmt.web.WebException;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.log.LoginLog;
import com.github.cjm0000000.mmt.web.log.persistence.SysLogRepository;
import com.github.cjm0000000.mmt.web.system.bean.User;
import com.github.cjm0000000.mmt.web.system.bean.UserConfig;
import com.github.cjm0000000.mmt.web.system.persistence.UserConfigRepository;
import com.github.cjm0000000.mmt.web.system.persistence.UserRepository;

/**
 * 用户授权管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MMTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	/** 用户存放密钥的KEY */
	private static final String ENCRYPY_KEY = "EncryptKey";
	@Autowired
	private UserRepository userMapper;
	@Autowired
	private UserConfigRepository userConfigMapper;
	@Autowired
	private SysLogRepository systemLogManager;
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public MMTAuthenticationFilter() {
		super(null);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if(username.length() < 3 || username.length() > 30)
			throw new AuthenticationServiceException("用户名长度必须在 3 - 30 位之间");
		if (password.length() <= 0)
			throw new AuthenticationServiceException("请输入密码");
		// 验证用户信息
		Integer user_id = userMapper.getUserIdByName(username);
		if(user_id == null){
			//用户不存在
			throw new AuthenticationServiceException("用户名不存在。");
		}
		//获取encryptKey
		UserConfig encryptKeyItem =  userConfigMapper.getItem(user_id,ENCRYPY_KEY);
		if(null == encryptKeyItem){
			throw new AuthenticationServiceException("您的密钥没有设置，请联系管理员。");
		}
		//验证用户名和密码
		User user = userMapper.checkLogin(username,SecureUtil.aesEncrypt(password, encryptKeyItem.getValue()));
		//保存日志
		saveLoginLog(request.getRemoteAddr(), user_id, username,user == null ? 0 : user.getRole_id(), user != null);
		//没有查到用户
		if(null == user)
			throw new AuthenticationServiceException("用户名和密码不匹配。");
		//判断是否锁定
		if(Status.AVAILABLE.equals(user.getIslock()))
			throw new AuthenticationServiceException("您的帐户已锁定，请联系系统管理员。");
		//登录成功，数据初始化
		request.getSession().setAttribute(MMTAction.TOKEN, user);
		//加载用户定制化信息
		//加载主页
		UserConfig indexConfig = userConfigMapper.getItem(user.getUser_id(), MMTAction.USER_CUSTOMIZATION_HOME);
		if(indexConfig != null)
			request.getSession().setAttribute(MMTAction.USER_CUSTOMIZATION_HOME, indexConfig.getValue());
		// UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		// 允许子类设置详细属性
		setDetails(request, authRequest);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString().trim();
	}

	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	 /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
	
	/**
	 * save login log
	 * @param ip
	 * @param user_id
	 * @param user_name
	 * @param role_id
	 * @param successLogin
	 */
	private void saveLoginLog(String ip, int user_id, String user_name,
			int role_id, boolean successLogin) {
		// 保存日志
		LoginLog log = new LoginLog();
		log.setLoginip(ip);
		log.setUser_name(user_name);
		log.setUser_id(user_id);
		log.setRole_id(role_id);
		if(successLogin)
			log.setLoginstatus(Status.AVAILABLE);
		else
			log.setLoginstatus(Status.UNAVAILABLE);
		if(systemLogManager.saveLoginLog(log) == 0)
			throw new WebException("登录日志保存失败。");
	}
}
