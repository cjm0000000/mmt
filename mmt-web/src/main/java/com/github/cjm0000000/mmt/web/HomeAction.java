package com.github.cjm0000000.mmt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.system.persistence.UserRepository;

/**
 * Home page
 * @author lemon
 * @version 1.0
 *
 */
@Controller
public class HomeAction extends MMTAction {
	@Autowired
	private UserRepository userMapper;
	
	/**
	 * show home page
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView(VIEW_HOME_PAGE,"",null);
	}
	
}
