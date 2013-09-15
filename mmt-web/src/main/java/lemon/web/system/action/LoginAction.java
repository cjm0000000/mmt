package lemon.web.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lemon.shared.entity.Status;
import lemon.shared.util.SecureUtil;
import lemon.web.base.MMTAction;
import lemon.web.log.bean.LoginLog;
import lemon.web.log.mapper.SystemLogManager;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * For login business
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class LoginAction extends MMTAction {
	/** 用户存放密钥的KEY */
	private static final String ENCRYPY_KEY = "EncryptKey";
	
	private static Log logger = LogFactory.getLog(LoginAction.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Autowired
	private SystemLogManager systemLogManager;
	
	/**
	 * verify user login
	 * @param user_name
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login")
	public ModelAndView login(String user_name,String password,HttpServletRequest request){
		if(null == user_name){
			return new ModelAndView(VIEW_LOGIN_PAGE,"",null);
		}
		Integer user_id = userMapper.getUserIdByName(user_name);
		//提示信息
		String msg = null;
		if(user_id == null){
			//用户不存在
			msg = "用户名不存在。";
			logger.debug(msg);
			return new ModelAndView(VIEW_LOGIN_PAGE,"msg",msg);
		}
		//获取encryptKey
		UserConfig encryptKeyItem =  userConfigMapper.getItem(user_id,ENCRYPY_KEY);
		if(null == encryptKeyItem){
			msg = "您的密钥没有设置，请联系管理员。";
			return new ModelAndView(VIEW_LOGIN_PAGE,"msg",msg);
		}
		//验证用户名和密码
		User user = userMapper.checkLogin(user_name,SecureUtil.aesEncrypt(password, encryptKeyItem.getValue()));
		//保存日志
		saveLoginLog(request.getRemoteAddr(), user_id, user_name,
				user == null ? 0 : user.getRole_id(), user != null);
		if(user != null){
			request.getSession().setAttribute(TOKEN, user);
			//TODO 修改主页menu
			request.getSession().setAttribute("index-menu-id", "2");
			return new ModelAndView("redirect:/"+VIEW_HOME_PAGE,"user",user);
		}else{
			msg = "用户名和密码不匹配。";
			return new ModelAndView(VIEW_LOGIN_PAGE,"msg",msg);
		}
		
	}
	
	/**
	 * logout
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session){
		session.removeAttribute(TOKEN);
		return new ModelAndView(VIEW_LOGOUT_PAGE,"msg","您已经成功退出。");
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
