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
import com.github.cjm0000000.mmt.yixin.YiXin;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;
import com.github.cjm0000000.mmt.yixin.config.persistence.YiXinConfigRepository;
import com.github.cjm0000000.mmt.yixin.message.process.SimpleYiXinMsgProcessor;

/**
 * 易信接口配置
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/interface/yixinconfig")
public class YiXinAction extends APIConfigAction {
	@Autowired
	private YiXinConfigRepository yxConfigMapper;

	/**
	 * 保存易信配置信息
	 * @param cfg
	 * @param apiStatus
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(YiXinConfig cfg, boolean apiStatus, ModelMap model){
		User user = (User) model.get(TOKEN);
		return processSave(user, cfg, apiStatus);
	}
	
	@Override
	protected ServiceType getServiceType() {
		return ServiceType.YIXIN;
	}

	@Override
	protected String getMainViewName() {
		return "interface/yixin-config";
	}

	@Override
	protected MmtConfig getConfig(int cust_id) {
		return yxConfigMapper.get(cust_id);
	}

	@Override
	protected String getFilterURL() {
		return "echat/";
	}

	@Override
	protected boolean isConfigExists(String api_url) {
		return yxConfigMapper.checkConfig(api_url) > 0;
	}

	@Override
	protected String getSimpleAPI() {
		return SimpleYiXinMsgProcessor.class.getName();
	}

	@Override
	protected String getAPIAccount(MmtConfig cfg) {
		YiXinConfig config = (YiXinConfig) cfg;
		return config.getYx_account();
	}

	@Override
	protected int saveConfig(MmtConfig cfg) {
		YiXinConfig config = (YiXinConfig) cfg;
		return yxConfigMapper.save(config);
	}

	@Override
	protected int updateConfig(MmtConfig cfg) {
		YiXinConfig config = (YiXinConfig) cfg;
		return yxConfigMapper.update(config);
	}

	@Override
	protected void flushCache(MmtConfig cfg) {
		YiXinConfig config = (YiXinConfig) cfg;
		YiXin.setConfig(config);
	}

	@Override
	protected String getAPIDisplayName() {
		return "易信";
	}

	@Override
	protected String getMenuURL() {
		return "interface/yixinconfig";
	}

}
