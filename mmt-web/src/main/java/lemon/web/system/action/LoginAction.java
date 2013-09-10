package lemon.web.system.action;

import lemon.web.system.bean.User;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * For login business
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class LoginAction {
	private static final String SUCCESS = "redirect:index.html";
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute("user")User user){
		System.out.println("Login...");
		System.out.println(user.getUser_name());
		User u = userMapper.checkLogin(user.getUser_name(), user.getPassword());
		if(u != null){
			//TODO set session or generate token
		}
		return SUCCESS;
	}
}
