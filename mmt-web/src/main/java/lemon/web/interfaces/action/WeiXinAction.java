package lemon.web.interfaces.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.bean.CustomerService;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.ServiceType;
import lemon.shared.entity.Status;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.AdminNavAction;
import lemon.web.global.MMT;
import lemon.web.system.bean.SystemConfig;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;
import lemon.web.ui.BS3UI;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.biz.customer.SimpleWeiXinMsgProcessor;
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
	@Autowired
	private SystemConfigMapper systemConfigMapper;

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
	 * @param cfg
	 * @param apiStatus
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpSession session, WeiXinConfig cfg, boolean apiStatus) {
		if(cfg == null)
			return BS3UI.warning("保存失败：信息不全。");
		if(!apiStatus && cfg.getCust_id() <= 0)
			return BS3UI.warning("非法访问。");
		int result = 0;
		if(cfg.getCust_id() <= 0){
			User user = (User) session.getAttribute(TOKEN);
			if(user == null)
				sendError("请先登录。");
			cfg.setCust_id(user.getCust_id());
			//判断api_url是否存在
			String api_url = SecureUtil.sha1(cfg.getWx_account());
			if(weiXinConfigMapper.checkConfig(api_url) > 0)
				api_url = SecureUtil.sha1(cfg.getWx_account() + System.currentTimeMillis());
			cfg.setToken(SecureUtil.md5(cfg.getWx_account() + System.currentTimeMillis()));
			cfg.setApi_url(api_url);
			cfg.setBiz_class(SimpleWeiXinMsgProcessor.class.getName());
			result = addService(cfg.getCust_id(), "0000-00-00 00:00");
			result = weiXinConfigMapper.save(cfg);
		}else{
			result = weiXinConfigMapper.update(cfg);
			if(!apiStatus)
				customerMapper.deleteService(cfg.getCust_id(), ServiceType.WEIXIN);
			else{
				if(customerMapper.getService(cfg.getCust_id(), ServiceType.WEIXIN) == null)
					addService(cfg.getCust_id(), "0000-00-00 00:00");
			}
		}
		//更新接口配置
		WeiXin.setConfig(weiXinConfigMapper.get(cfg.getCust_id()));
		if(result != 0)
			return BS3UI.success("微信接口配置成功。");
		else
			return BS3UI.danger("微信接口配置失败。");
	}
	
	/**
	 * 显示微信配置信息
	 * @param session
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="show")
	public ModelAndView showConfig(HttpSession session, Integer cust_id) {
		if (null == session)
			sendError("您登录超时，请重新登录。");
		User user = (User) session.getAttribute(TOKEN);
		if (null == user)
			sendError("您登录超时，请重新登录。");
		if(user.getRole_id() != 1)
			cust_id = user.getCust_id();
		else{
			if(null == cust_id || cust_id <= 0)
				cust_id = user.getCust_id();
		}
		if(cust_id <= 0)
			sendError("客户信息不存在。");
		// 获取Main视图名称
		String mainViewName = "interface/weixin-config";
		// 获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		// 获取Main数据
		Customer cust = customerMapper.getCustomer(cust_id);
		// 获取WeiXinConfig
		WeiXinConfig wxcfg = weiXinConfigMapper.get(cust_id);
		// 获取CustomerService
		CustomerService service = customerMapper.getService(cust_id, ServiceType.WEIXIN);
		SystemConfig syscfg = systemConfigMapper.getItem(DOMAIN_KEY);
		if(null == syscfg)
			sendError("请先配置域名。");
		if(wxcfg != null)
			wxcfg.setApi_url(syscfg.getValue().trim() + MMT.getContextRoot() + "weixinGW/" + wxcfg.getApi_url());
		resultMap.put("cfg", wxcfg);
		resultMap.put("cust", cust);
		resultMap.put("service", service);
		resultMap.put("mainViewName", mainViewName);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}

	@Override
	public String getMenuURL() {
		return "interface/weixinconfig";
	}
	
	/**
	 * 添加服务
	 * @param cust_id
	 * @param expireTime
	 * @return
	 */
	private int addService(int cust_id, String expireTime){
		CustomerService service = new CustomerService();
		service.setCust_id(cust_id);
		service.setExpire_time(expireTime);
		service.setService(ServiceType.WEIXIN);
		service.setStatus(Status.AVAILABLE);
		return customerMapper.addService(service);
	}

}
