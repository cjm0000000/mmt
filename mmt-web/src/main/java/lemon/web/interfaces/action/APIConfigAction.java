package lemon.web.interfaces.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

import lemon.shared.api.simple.MMTConfig;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public abstract class APIConfigAction extends AdminNavAction {
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	
	/**
	 * 获取API服务类型
	 * @return
	 */
	protected abstract ServiceType getServiceType();
	
	/**
	 * 获取主页模板名称
	 * @return
	 */
	protected abstract String getMainViewName();
	
	/**
	 * 获取接口配置
	 * @param cust_id
	 * @return
	 */
	protected abstract MMTConfig getConfig(int cust_id);
	
	/**
	 * 获取API过滤器路径
	 * @return
	 */
	protected abstract String getFilterURL();
	
	/**
	 * 判断配置是否存在
	 * @param api_url
	 * @return
	 */
	protected abstract boolean isConfigExists(String api_url);
	
	/**
	 * 获取API实现
	 * @return
	 */
	protected abstract String getSimpleAPI();
	
	/**
	 * 获取接口账号
	 * @return
	 */
	protected abstract String getAPIAccount(MMTConfig cfg);
	
	/**
	 * 保存配置
	 * @param cfg
	 * @return
	 */
	protected abstract int saveConfig(MMTConfig cfg);
	
	/**
	 * 更新配置
	 * @param cfg
	 * @return
	 */
	protected abstract int updateConfig(MMTConfig cfg);
	
	/**
	 * 更新缓存
	 * @param cfg
	 */
	protected abstract void flushCache(MMTConfig cfg);
	
	/**
	 * 获取接口显示名称
	 * @return
	 */
	protected abstract String getAPIDisplayName();

	/**
	 * 跳转到配置信息页
	 * @return
	 */
	@RequestMapping("list")
	public final String list() {
		return "redirect:show";
	}
	
	/**
	 * 保存接口配置信息
	 * @param session
	 * @param cfg
	 * @param apiStatus
	 * @return
	 */
	public final String processSave(HttpSession session, MMTConfig cfg, boolean apiStatus) {
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
			String account = getAPIAccount(cfg);
			String api_url = SecureUtil.sha1(account);
			if(isConfigExists(api_url))
				api_url = SecureUtil.sha1(account + System.currentTimeMillis());
			cfg.setToken(SecureUtil.md5(account + System.currentTimeMillis()));
			cfg.setApi_url(api_url);
			cfg.setBiz_class(getSimpleAPI());
			result = addService(cfg.getCust_id(), "0000-00-00 00:00");
			result = saveConfig(cfg);
		}else{
			result = updateConfig(cfg);
			if(!apiStatus)
				customerMapper.deleteService(cfg.getCust_id(), getServiceType());
			else{
				if(customerMapper.getService(cfg.getCust_id(), getServiceType()) == null)
					addService(cfg.getCust_id(), "0000-00-00 00:00");
			}
		}
		//更新接口配置
		flushCache(getConfig(cfg.getCust_id()));
		if(result != 0)
			return BS3UI.success(getAPIDisplayName() + "接口配置成功。");
		else
			return BS3UI.danger(getAPIDisplayName() + "接口配置失败。");
	}
	
	/**
	 * 显示接口配置信息
	 * @param session
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="show")
	public final ModelAndView showConfig(HttpSession session, Integer cust_id) {
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
		// 获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		// 获取Main数据
		Customer cust = customerMapper.getCustomer(cust_id);
		// 获取MMTConfig
		MMTConfig mmtcfg = getConfig(cust_id);
		// 获取服务类型
		CustomerService service = customerMapper.getService(cust_id, getServiceType());
		//获取系统域名
		SystemConfig syscfg = systemConfigMapper.getItem(DOMAIN_KEY);
		if(null == syscfg)
			sendError("请先配置域名。");
		if(mmtcfg != null)
			mmtcfg.setApi_url(syscfg.getValue().trim() + MMT.getContextRoot() + getFilterURL() + mmtcfg.getApi_url());
		resultMap.put("cfg", mmtcfg);
		resultMap.put("cust", cust);
		resultMap.put("service", service);
		resultMap.put("mainViewName", getMainViewName());
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
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
		service.setService(getServiceType());
		service.setStatus(Status.AVAILABLE);
		return customerMapper.addService(service);
	}

}
