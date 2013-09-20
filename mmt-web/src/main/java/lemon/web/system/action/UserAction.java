package lemon.web.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system/user")
public final class UserAction extends AdminNavAction {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 显示用户列表首页
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:list/1";
	}
	
	/**
	 * 分页显示用户列表
	 * @param page
	 * @param user_name
	 * @param session
	 * @return
	 */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable("page") int page, String user_name,
			HttpSession session) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		if(null == mainViewName)
			sendNotFoundError();
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		List<User> userList = userMapper.getUserList((page - 1) * PAGESIZE,
				PAGESIZE, user_name);
		int userCnt = userMapper.getUserCnt(user_name);
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("userList", userList);
		resultMap.put("userCnt", userCnt);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	
	/**
	 * show subsystem home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("add")
	public String add(@PathVariable String second, HttpSession session) {
		
		return VIEW_ADD;
	}
	
	/**
	 * show subsystem home page
	 * @param second
	 * @param session
	 * @return
	 */
	@RequestMapping("delete")
	public String delete(@PathVariable String second,@PathVariable String third, HttpSession session) {
		return VIEW_DELETE;
	}

	@Override
	protected String getMenuURL() {
		return "system/user";
	}

}
