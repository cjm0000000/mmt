package com.github.cjm0000000.mmt.web.customer.action;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.github.cjm0000000.mmt.web.common.AdminNavAction;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.common.paging.Pagination;
import com.github.cjm0000000.mmt.web.system.bean.User;

/**
 * 客户信息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/customer/information")
@SessionAttributes(MMTAction.TOKEN)
public final class CustInfoAction extends AdminNavAction {
	@Autowired
	private CustomerRepository customerMapper;

	/**
	 * 显示客户信息列表首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:/webservices/customer/information/list/1";
	}
	
	/**
	 * 分页显示客户信息列表
	 * @param page
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list/{page}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取operation
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<Customer> custList = customerMapper.getCustomerList((page - 1) * PAGESIZE, PAGESIZE, key);
		obtainServices(custList);
		Pagination pg = new Pagination(page, PAGESIZE,
				customerMapper.getCustCnt(key),
				(key == null || "".equals(key)) ? null : "k=" + key);
		ModelAndView mv = getListResultByPagination(pg, user.getRole_id(), operation, custList);
		mv.addObject("k", key);
		return mv;
	}
	

	/**
	 * 保存客户信息
	 * @param cust
	 * @param br
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@Valid Customer cust, BindingResult br) {
		if(cust == null)
			return sendJSONError("客户信息保存失败：信息不全。");
		if(br.hasErrors())
			return sendJSONError(br.getFieldError().getDefaultMessage());
		int result = 0;
		if(cust.getCust_id() <= 0){
			cust.setStatus(Status.AVAILABLE);
			result = customerMapper.addCustomer(cust);
		}else
			result = customerMapper.updateCustomer(cust);
		if(result != 0)
			return sendJSONMsg("客户信息保存成功。");
		else
			return sendJSONError("客户信息保存失败。");
	}
	
	/**
	 * 删除客户信息
	 * @param cust_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String delete(int cust_id) {
		if (cust_id <= 0)
			return sendJSONError("客户信息删除失败： 客户不存在。");
		int result = customerMapper.delete(cust_id);
		if (0 != result)
			return sendJSONMsg("客户信息删除成功。");
		else
			return sendJSONError("客户信息删除失败。");
	}
	
	/**
	 * 显示添加或者编辑客户信息的页面
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value = "add-edit-page", method = RequestMethod.GET)
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
		for (Customer customer : list) 
			customer.setServices(customerMapper.getServices(customer.getCust_id()));
	}

}