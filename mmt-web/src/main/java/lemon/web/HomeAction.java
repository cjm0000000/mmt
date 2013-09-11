package lemon.web;

import javax.servlet.http.HttpSession;

import lemon.web.system.bean.User;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class HomeAction {
	private static final String HOME_PAGE = "index";
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * show home page
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpSession session){
		User user = (User) session.getAttribute("User");
		return HOME_PAGE;
	}
	
	
	
}
