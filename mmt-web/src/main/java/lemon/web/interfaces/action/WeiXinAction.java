package lemon.web.interfaces.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.bean.CustomerService;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.ServiceType;
import lemon.shared.entity.Status;
import lemon.web.base.AdminNavAction;
import lemon.web.system.bean.User;
import lemon.web.ui.BS3UI;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.dao.WXConfigMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 微信接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/interface/weixinconfig")
public final class WeiXinAction extends AdminNavAction {
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private WXConfigMapper weiXinConfigMapper;

	/**
	 * 跳转到配置信息页
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:show";
	}
	
	/**
	 * 保存微信配置信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(Customer cust) {
		if(cust == null)
			return BS3UI.warning("保存失败：信息不全。");
		int result = 0;
		if(cust.getCust_id() <= 0){
			cust.setStatus(Status.AVAILABLE);
			result = customerMapper.addCustomer(cust);
		}else
			result = customerMapper.updateCustomer(cust);
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
	}
	
	/**
	 * 显示微信配置信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="show")
	public ModelAndView showConfig(HttpSession session) {
		if (null == session)
			sendError("您登录超时，请重新登录。");
		User user = (User) session.getAttribute(TOKEN);
		if (null == user)
			sendError("您登录超时，请重新登录。");
		// 获取Main视图名称
		String mainViewName = "interface/weixin-config";
		// 获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		// 获取Main数据
		Customer cust = customerMapper.getCustomer(user.getCust_id());
		// 获取WeiXinConfig
		WeiXinConfig cfg = weiXinConfigMapper.get(cust.getCust_id());
		// 获取CustomerService
		CustomerService service = customerMapper.getService(cust.getCust_id(),
				ServiceType.WEIXIN);
		resultMap.put("cfg", cfg);
		resultMap.put("service", service);
		resultMap.put("mainViewName", mainViewName);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}

	@Override
	public String getMenuURL() {
		return "interface/weixinconfig";
	}

}
