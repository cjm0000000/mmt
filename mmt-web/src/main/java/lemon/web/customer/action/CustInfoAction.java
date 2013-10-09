package lemon.web.customer.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.Status;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
		obtainServices(custList);
		int rsCnt = customerMapper.getCustCnt();
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("custList", custList);
		resultMap.put("rsCnt", rsCnt);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}
	

	/**
	 * 保存客户信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public String save(Customer cust) {
		if(cust == null)
			return "\u00ef\u00bb\u00bf\u00e6\u00b7\u00bb\u00e5\u008a\u00a0\u00e5\u00a4\u00b1\u00e8\u00b4\u00a5\u00ef\u00bc\u009a\u00e5\u00ae\u00a2\u00e6\u0088\u00b7\u00e4\u00bf\u00a1\u00e6\u0081\u00af\u00e4\u00b8\u008d\u00e5\u00ad\u0098\u00e5\u009c\u00a8\u00e3\u0080\u0082";
		int result = 0;
		if(cust.getCust_id() <= 0){
			cust.setStatus(Status.AVAILABLE);
			result = customerMapper.addCustomer(cust);
		}else
			result = customerMapper.updateCustomer(cust);
		System.out.println(result);
		if(result != 0)
			return "\u00ef\u00bb\u00bf\u00e4\u00bf\u009d\u00e5\u00ad\u0098\u00e6\u0088\u0090\u00e5\u008a\u009f\u00e3\u0080\u0082";
		else
			return "\u00ef\u00bb\u00bf\u00e4\u00bf\u009d\u00e5\u00ad\u0098\u00e5\u00a4\u00b1\u00e8\u00b4\u00a5\u00e3\u0080\u0082";
	}
	
	/**
	 * 删除客户信息
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(int cust_id) {
		if (cust_id <= 0)
			return "\u00ef\u00bb\u00bf\u00e5\u0088\u00a0\u00e9\u0099\u00a4\u00e5\u00a4\u00b1\u00e8\u00b4\u00a5\u00ef\u00bc\u009a\u00e5\u00ae\u00a2\u00e6\u0088\u00b7\u00e4\u00bf\u00a1\u00e6\u0081\u00af\u00e4\u00b8\u008d\u00e5\u00ad\u0098\u00e5\u009c\u00a8\u00e3\u0080\u0082";
		int result = customerMapper.delete(cust_id);
		if (0 != result)
			return "\u00ef\u00bb\u00bf\u00e5\u0088\u00a0\u00e9\u0099\u00a4\u00e6\u0088\u0090\u00e5\u008a\u009f\u00e3\u0080\u0082";
		else
			return "\u00ef\u00bb\u00bf\u00e5\u0088\u00a0\u00e9\u0099\u00a4\u00e5\u00a4\u00b1\u00e8\u00b4\u00a5\u00e3\u0080\u0082";
	}
	
	/**
	 * 显示添加或者编辑客户信息的页面
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(int cust_id) {
		Customer cust = null;
		if(cust_id > 0)
			cust = customerMapper.getCustomer(cust_id);
		if(null == cust)
			cust = new Customer();
		return new ModelAndView(getAddEditView(), "cust", cust);
	}

	@Override
	public String getMenuURL() {
		return "customer/information";
	}
	
	/**
	 * 查询服务开通情况
	 * @param list
	 */
	private void obtainServices(List<Customer> list){
		for (Customer customer : list) {
			customer.setServices(customerMapper.getServices(customer.getCust_id()));
		}
	}

}
