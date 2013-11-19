package lemon.web.interfaces.action;

import lemon.shared.config.MMTConfig;
import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.global.MMT;
import lemon.web.system.bean.SystemConfig;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;
import lemon.web.ui.BS3UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@SessionAttributes(MMTAction.TOKEN)
public abstract class APIConfigAction extends AdminNavAction {
	@Autowired
	private CustomerRepository customerMapper;
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
	 * @param user
	 * @param cfg
	 * @param apiStatus
	 * @return
	 */
	public final String processSave(User user, MMTConfig cfg, boolean apiStatus) {
		if(cfg == null)
			return BS3UI.warning("保存失败：信息不全。");
		if(!apiStatus && cfg.getCust_id() <= 0)
			return BS3UI.warning("非法访问。");
		int result = 0;
		if(cfg.getCust_id() <= 0){
			cfg.setCust_id(user.getCust_id());
			//判断api_url是否存在
			String account = getAPIAccount(cfg);
			String api_url = SecureUtil.sha1(account);
			while(isConfigExists(api_url))
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
	 * @param user
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="show")
	public final ModelAndView showConfig(@ModelAttribute(TOKEN) User user, 
			@RequestParam(value = "cust_id", required = false, defaultValue = "0") int cust_id) {
		System.out.println("==============="+user);
		if(user.getRole_id() != 1)
			cust_id = user.getCust_id();
		else
			if(cust_id <= 0)
				cust_id = user.getCust_id();
		if(cust_id <= 0)
			sendError("客户信息不存在。");
		//获取系统域名,确保快速失败
		SystemConfig syscfg = systemConfigMapper.getItem(DOMAIN_KEY, DOMAIN_KEY);
		if(null == syscfg)
			sendError("请先到“系統管理——系統配置”模块配置域名。");
		// 获取Main数据
		Customer cust = customerMapper.getCustomer(cust_id);
		// 获取MMTConfig
		MMTConfig mmtcfg = getConfig(cust_id);
		if(mmtcfg != null)
			mmtcfg.setApi_url(syscfg.getValue().trim() + MMT.getContextRoot() + getFilterURL() + mmtcfg.getApi_url());
		// 获取服务类型
		CustomerService service = customerMapper.getService(cust_id, getServiceType());
		// 生成结果
		ModelAndView mv = getListResult(user.getRole_id(), "config", null);
		mv.addObject("cfg", mmtcfg);
		mv.addObject("cust", cust);
		mv.addObject("service", service);
		mv.addObject("mainViewName", getMainViewName());
		return mv;
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
		service.setService_type(getServiceType());
		service.setStatus(Status.AVAILABLE);
		service.setId(IdWorkerManager.getIdWorker(CustomerService.class).getId());
		return customerMapper.addService(service);
	}

}
