package com.github.cjm0000000.mmt.web.interfaces.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.customer.Customer;
import com.github.cjm0000000.mmt.shared.customer.CustomerService;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomerRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;
import com.github.cjm0000000.mmt.shared.toolkit.secure.SecureUtil;
import com.github.cjm0000000.mmt.web.common.AdminNavAction;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.global.MMT;
import com.github.cjm0000000.mmt.web.system.bean.SystemConfig;
import com.github.cjm0000000.mmt.web.system.bean.User;
import com.github.cjm0000000.mmt.web.system.persistence.SysConfigRepository;
import com.github.cjm0000000.mmt.web.ui.BS3UI;

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
	private SysConfigRepository systemConfigMapper;
	private static final int NO_EXPIRE_TIME = 0;
	
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
	protected abstract MmtConfig getConfig(int cust_id);
	
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
	protected abstract String getAPIAccount(MmtConfig cfg);
	
	/**
	 * 保存配置
	 * @param cfg
	 * @return
	 */
	protected abstract int saveConfig(MmtConfig cfg);
	
	/**
	 * 更新配置
	 * @param cfg
	 * @return
	 */
	protected abstract int updateConfig(MmtConfig cfg);
	
	/**
	 * 更新缓存
	 * @param cfg
	 */
	protected abstract void flushCache(MmtConfig cfg);
	
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
	@Transactional(propagation = Propagation.REQUIRED)
	public final String processSave(User user, MmtConfig cfg, boolean apiStatus) {
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
			result = addService(cfg.getCust_id(), NO_EXPIRE_TIME);
			result = saveConfig(cfg);
		}else{
			result = updateConfig(cfg);
			if(!apiStatus)
				customerMapper.deleteService(cfg.getCust_id(), getServiceType());
			else{
				if(customerMapper.getService(cfg.getCust_id(), getServiceType()) == null)
					addService(cfg.getCust_id(), NO_EXPIRE_TIME);
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
	 * @param cust_id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public final ModelAndView showConfig(
			@RequestParam(value = "cust_id", required = false, defaultValue = "0") int cust_id,
			ModelMap model) {
		User user = (User) model.get(TOKEN);
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
		MmtConfig mmtcfg = getConfig(cust_id);
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
	private int addService(int cust_id, int expireTime){
		CustomerService service = new CustomerService();
		service.setCust_id(cust_id);
		service.setExpire_time(expireTime);
		service.setService_type(getServiceType());
		service.setStatus(Status.AVAILABLE);
		service.setId(IdWorkerManager.getIdWorker(CustomerService.class).getId());
		return customerMapper.addService(service);
	}

}
