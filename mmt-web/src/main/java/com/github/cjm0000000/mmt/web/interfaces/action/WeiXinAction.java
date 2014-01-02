package com.github.cjm0000000.mmt.web.interfaces.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.web.system.bean.User;
import com.github.cjm0000000.mmt.weixin.WeiXin;
import com.github.cjm0000000.mmt.weixin.api.passive.SimpleWeiXinMsgProcessor;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

/**
 * 微信接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/interface/weixinconfig")
public class WeiXinAction extends APIConfigAction {
	@Autowired
	private WeiXinConfigRepository weiXinConfigMapper;
	
	/**
	 * 保存微信配置信息
	 * @param cfg
	 * @param apiStatus
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(WeiXinConfig cfg, boolean apiStatus, ModelMap model){
		User user = (User) model.get(TOKEN);
		return processSave(user, cfg, apiStatus);
	}

	@Override
	protected ServiceType getServiceType() {
		return ServiceType.WEIXIN;
	}

	@Override
	protected String getMainViewName() {
		return "interface/weixin-config";
	}

	@Override
	protected MmtConfig getConfig(int cust_id) {
		return weiXinConfigMapper.get(cust_id);
	}

	@Override
	protected String getFilterURL() {
		return "weichat/";
	}

	@Override
	protected boolean isConfigExists(String api_url) {
		return weiXinConfigMapper.checkConfig(api_url) > 0;
	}

	@Override
	protected String getSimpleAPI() {
		return SimpleWeiXinMsgProcessor.class.getName();
	}

	@Override
	protected String getAPIAccount(MmtConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return config.getWx_account();
	}

	@Override
	protected int saveConfig(MmtConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return weiXinConfigMapper.save(config);
	}

	@Override
	protected int updateConfig(MmtConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return weiXinConfigMapper.update(config);
	}

	@Override
	protected void flushCache(MmtConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		WeiXin.setConfig(config);
	}

	@Override
	protected String getMenuURL() {
		return "interface/weixinconfig";
	}

	@Override
	protected String getAPIDisplayName() {
		return "微信";
	}
	
}
