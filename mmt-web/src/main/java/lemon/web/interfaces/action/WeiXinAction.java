package lemon.web.interfaces.action;


import javax.servlet.http.HttpSession;

import lemon.shared.api.simple.MMTConfig;
import lemon.shared.entity.ServiceType;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.biz.customer.SimpleWeiXinMsgProcessor;
import lemon.weixin.dao.WXConfigMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/interface/weixinconfig")
public final class WeiXinAction extends APIConfigAction {
	@Autowired
	private WXConfigMapper weiXinConfigMapper;
	
	/**
	 * 保存微信配置信息
	 * @param session
	 * @param cfg
	 * @param apiStatus
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpSession session, WeiXinConfig cfg, boolean apiStatus){
		return processSave(session, cfg, apiStatus);
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
	protected MMTConfig getConfig(int cust_id) {
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
	protected String getAPIAccount(MMTConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return config.getWx_account();
	}

	@Override
	protected int saveConfig(MMTConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return weiXinConfigMapper.save(config);
	}

	@Override
	protected int updateConfig(MMTConfig cfg) {
		WeiXinConfig config = (WeiXinConfig)cfg;
		return weiXinConfigMapper.update(config);
	}

	@Override
	protected void flushCache(MMTConfig cfg) {
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
