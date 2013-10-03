package lemon.web.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public ModelAndView login(@Valid User u,BindingResult result,
			HttpServletRequest request) {
		if(result.hasErrors()){
			return info(result.getFieldError().getDefaultMessage(),u.getUsername());
		}
		Integer user_id = userMapper.getUserIdByName(u.getUsername());
		if(user_id == null){
			//用户不存在
			return info("用户名不存在。",u.getUsername());
		}
		//获取encryptKey
		UserConfig encryptKeyItem =  userConfigMapper.getItem(user_id,ENCRYPY_KEY);
		if(null == encryptKeyItem){
			return info("您的密钥没有设置，请联系管理员。",u.getUsername());
		}
		//验证用户名和密码
		User user = userMapper.checkLogin(u.getUsername(),SecureUtil.aesEncrypt(u.getPassword(), encryptKeyItem.getValue()));
		//保存日志
		saveLoginLog(request.getRemoteAddr(), user_id, u.getUsername(),user == null ? 0 : user.getRole_id(), user != null);
		//没有查到用户
		if(null == user){
			return info("用户名和密码不匹配。",u.getUsername());
		}
		//登录成功，数据初始化
		request.getSession().setAttribute(TOKEN, user);
		//加载用户定制化信息
		//加载主页
		UserConfig indexConfig = userConfigMapper.getItem(user.getUser_id(), USER_CUSTOMIZATION_HOME);
		if(indexConfig != null)
			request.getSession().setAttribute(USER_CUSTOMIZATION_HOME, indexConfig.getValue());
		return new ModelAndView("redirect:/"+VIEW_HOME_PAGE,"user",user);
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
	 * Forbidden
	 * @param session
	 * @return
	 */
	@RequestMapping("/forbidden")
	public String forbidden(HttpSession session){
		session.removeAttribute(TOKEN);
		return VIEW_FORBIDDEN;
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
