package lemon.web.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.bean.UserConfig;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.UserConfigMapper;
import lemon.web.system.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.shared.customer.Customer;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomerRepository;

/**
 * 用户管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/system/user")
@SessionAttributes(MMTAction.TOKEN)
public class UserAction extends AdminNavAction {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private CustomerRepository customerMapper;

	/**
	 * 显示用户列表首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:/webservices/system/user/list/1";
	}
	
	/**
	 * 分页显示用户列表
	 * @param page
	 * @param user_name
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list/{page}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page, String user_name,
			ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取操作名称
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<User> userList = userMapper.getUserList((page - 1) * PAGESIZE, PAGESIZE, user_name);
		int userCnt = userMapper.getUserCnt(user_name);
		Pagination pg = new Pagination(page, PAGESIZE, userCnt);
		return getListResultByPagination(pg, user.getRole_id(), operation, userList);
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param result
	 * @return
	 */
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value="save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@Valid User user, BindingResult result) {
		if(user == null)
			return sendJSONError("参数不能为空。");
		if(result.hasErrors())
			return sendJSONError(result.getFieldError().getDefaultMessage());
		if(user.getUser_id() <= 0){
			if(userMapper.getUserIdByName(user.getUsername()) != null)
				return sendJSONError("用户名[" + user.getUsername() + "]已经存在。");
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
			userMapper.addUserRole(user.getUser_id(), user.getRole_id(), user.getCust_id());
			//保存首页配置
			UserConfig indexConfig = new UserConfig();
			indexConfig.setUser_id(user.getUser_id());
			indexConfig.setKey(USER_CUSTOMIZATION_HOME);
			//TODO 首页配置最好从页面获取
			indexConfig.setValue("message/level1");
			userConfigMapper.addItem(indexConfig);
		}else{
			Integer user_id;
			if ((user_id = userMapper.getUserIdByName(user.getUsername())) != null
					&& user_id.intValue() != user.getUser_id())
				return sendJSONError("用户名[" + user.getUsername() + "]已经存在。");
			userMapper.updateUser(user);
		}
		return sendJSONMsg("用户信息保存成功。");
	}
	
	/**
	 * 更改用户锁定状态
	 * @param user_id
	 * @param isLock
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="update-lock", method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateLockStatus(@RequestParam int user_id,
			@RequestParam(required = false) Status islock) {
		if(user_id <= 0)
			return sendJSONError("用户不存在。");
		if(islock == null)
			return sendJSONError("参数错误。");
		User u = userMapper.getUserById(user_id);
		u.setIslock(islock);
		int result = userMapper.updateUser(u);
		if(result != 0)
			return sendJSONMsg("更新成功。");
		return sendJSONError("更新失败。");
	}
	
	/**
	 * 删除用户
	 * @param user_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
	@RequestMapping(value = "add-edit-page", method = RequestMethod.GET)
	public ModelAndView addOrEditPage(int user_id) {
		User user = null;
		if (user_id != 0)
			user = userMapper.getUserById(user_id);
		if (user == null)
			user = new User();
		List<Role> roleList = roleMapper.getRoleList(0, 0);
		List<Customer> custList = customerMapper.getCustomerList(0, -1, null);
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
