package lemon.web.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.Status;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private CustomerMapper customerMapper;

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
		if (userList.size() == 0 && page > 1)
			return new ModelAndView("redirect:" + lastPage(page, userCnt));
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("userList", userList);
		resultMap.put("userCnt", userCnt);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(@Valid User user, BindingResult result) {
		if(user == null)
			return sendJSONError("参数不能为空。");
		if(result.hasErrors())
			return sendJSONError(result.getFieldError().getDefaultMessage());
		if(user.getUser_id() <= 0){
			user.setStatus(Status.AVAILABLE);
			//生成密钥
			String secureKey = SecureUtil.generateSecretKey(128);
			user.setPassword(SecureUtil.aesEncrypt(user.getPassword(), secureKey));
			userMapper.addUser(user);
			//保存密钥
			UserConfig item = new UserConfig();
			item.setKey(ENCRYPY_KEY);
			item.setUser_id(user.getUser_id());
			item.setValue(secureKey);
			userConfigMapper.addItem(item);
			//保存角色信息
			userMapper.addUserRole(user.getUser_id(), user.getRole_id(),
					user.getCust_id());
		}else{
			userMapper.updateUser(user);
		}
		return sendJSONMsg("用户信息保存成功。");
	}
	
	/**
	 * 删除用户
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(String user_id) {
		if (user_id == null || "".equals(user_id))
			return sendJSONError("删除失败： 用户不存在。 ");
		String[] userIds = user_id.split(",");
		userMapper.deleteUser(userIds);
		return sendJSONMsg("用户删除成功。");
	}
	
	/**
	 * 显示添加或者编辑用户的页面
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(int user_id) {
		User user = null;
		if (user_id != 0)
			user = userMapper.getUserById(user_id);
		if (user == null)
			user = new User();
		List<Role> roleList = roleMapper.getRoleList(0, 0);
		List<Customer> custList = customerMapper.getCustomerList(0, 10000);
		Map<String, Object> result = new HashMap<>();
		result.put("user", user);
		result.put("roleList", roleList);
		result.put("custList", custList);
		return new ModelAndView(getAddEditView(), "result", result);
	}

	@Override
	protected String getMenuURL() {
		return "system/user";
	}

}
