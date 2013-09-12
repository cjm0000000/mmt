package lemon.web;

import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home page
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class HomeAction {
	private static final String VIEW_HOME_PAGE = "index";
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * show home page
	 * @param session
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(){
		return new ModelAndView(VIEW_HOME_PAGE,"",null);
	}
	
	
	
}
