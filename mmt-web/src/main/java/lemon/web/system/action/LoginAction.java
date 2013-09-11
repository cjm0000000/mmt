package lemon.web.system.action;

import javax.servlet.http.HttpSession;

import lemon.web.system.bean.User;
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
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * verify user login
	 * @param user_name
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(String user_name,String password,HttpSession session){
		User user = userMapper.checkLogin(user_name,password);
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
