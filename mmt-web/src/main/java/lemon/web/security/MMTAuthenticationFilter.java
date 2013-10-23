package lemon.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lemon.shared.entity.Status;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.MMTAction;
import lemon.web.log.bean.LoginLog;
import lemon.web.log.mapper.SystemLogManager;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

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
	private UserMapper userMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Autowired
	private SystemLogManager systemLogManager;
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public MMTAuthenticationFilter() {
		super(null);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		System.out.println("================Begin MMTAuthenticationFilter======");
		String username = obtainUsername(request);
		String password = obtainPassword(request);

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
		if(null == user){
			throw new AuthenticationServiceException("用户名和密码不匹配。");
		}
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
		systemLogManager.saveLoginLog(log);
	}
}
