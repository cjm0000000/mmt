package lemon.web.customer.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 客户信息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/customer/information")
public final class CustInfoAction extends AdminNavAction {
	@Autowired
	private CustomerMapper customerMapper;

	/**
	 * 显示客户信息列表首页
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:list/1";
	}
	
	/**
	 * 分页显示客户信息列表
	 * @param page
	 * @param user_name
	 * @param session
	 * @return
	 */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable("page") int page, String cust_name,
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
		List<Customer> custList = customerMapper.getCustomerList((page - 1) * PAGESIZE, PAGESIZE);
		int rsCnt = customerMapper.getCustCnt();
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("custList", custList);
		resultMap.put("rsCnt", rsCnt);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	

	/**
	 * 添加客户信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String add(HttpSession session) {
		//TODO 添加客户信息
		return "";
	}
	
	/**
	 * 删除客户信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public String delete( HttpSession session) {
		//TODO 删除客户信息
		return "";
	}
	
	/**
	 * 编辑客户信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(HttpSession session) {
		//TODO 编辑客户信息
		return "";
	}
	
	/**
	 * 显示添加或者编辑客户信息的页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public String addOrEditPage(HttpSession session) {
		//TODO 显示添加或者编辑客户信息的页面
		return VIEW_ADD_EDIT;
	}

	@Override
	public String getMenuURL() {
		return "customer/information";
	}

}
