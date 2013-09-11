package lemon.web.system.action;

import javax.servlet.http.HttpSession;

import lemon.shared.util.SecureUtil;
import lemon.web.log.bean.LoginLog;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * For login business
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class LoginAction {
	private static final String HOME_PAGE = "redirect:/";
	private static final String ENCRYPY_KEY = "EncryptKey";
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	//TODO 添加
	
	/**
	 * verify user login
	 * @param user_name
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(String user_name,String password,HttpSession session){
		int user_id = userMapper.getUserIdByName(user_name);
		if(user_id <= 0){
			//用户不存在
		}
		//获取encryptKey
		UserConfig encryptKeyItem =  userConfigMapper.getItem(user_id,ENCRYPY_KEY);
		if(null == encryptKeyItem){
			//TODO handle error
		}
		//验证用户名和密码
		User user = userMapper.checkLogin(user_name,SecureUtil.aesEncrypt(password, encryptKeyItem.getValue()));
		//保存日志
		LoginLog log = new LoginLog();
		log.setLoginip("");
		log.setUser_name(user_name);
		log.setUser_id(user_id);
		//TODO 设置LOG
		if(user != null){
			session.setAttribute("user", user);
		}
		
		return new ModelAndView(HOME_PAGE,"user",user);
	}
	
	/**
	 * logout
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return HOME_PAGE;
	}
	
}
