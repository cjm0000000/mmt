package lemon.weixin;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.api.MsgParser;
import lemon.shared.api.simple.AbstractMmtAPI;
import lemon.shared.api.simple.MMTConfig;
import lemon.shared.entity.Action;
import lemon.shared.log.bean.CustomMenuLog;
import lemon.shared.request.bean.ReturnCode;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.http.HttpConnector;
import lemon.weixin.config.WeiXin;
import lemon.weixin.config.bean.AccountType;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.log.bean.MsgLog;
import lemon.weixin.log.mapper.WXLogManager;
import lemon.weixin.message.parser.WXMsgParser;

/**
 * The WeiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("weiXinAPI")
public final class WeiXinAPI extends AbstractMmtAPI {
	private static Log logger = LogFactory.getLog(WeiXinAPI.class);
	@Autowired
	private WXLogManager wxLogManager;
	private MsgParser parser;

	@Override
	public String processMsg(String token, String msg) {
		WeiXinConfig cfg = WeiXin.getConfig(token);
		int cust_id = cfg.getCust_id();
		logger.debug("Receive a message: " + msg);
		//save received log
		saveReciveMessageLog(cust_id, msg);
		//get message parser
		parser = WXMsgParser.getParser(msg);
		if(null == parser)
			throw new WeiXinException("No parser find.");
		//process message and generate replay message
		String rMsg = parser.parseMessage(token, msg);
		logger.debug("Generate a reply message: " + rMsg);
		//save reply log
		if(rMsg != null)
			saveSendMessageLog(cust_id, rMsg);
		//replay WeiXin message
		return rMsg;
	}
	
	@Override
	public ReturnCode createMenus(MMTConfig config, String menuJson) {
		WeiXinConfig cfg = (WeiXinConfig) config;
		if(cfg == null)
			throw new WeiXinException("客户微信配置信息不存在。");
		if(cfg.getAccount_type().equals(AccountType.DY))
			throw new WeiXinException("订阅号不支持自定义菜单操作。");
		//发送请求
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		String result = HttpConnector.post(WeiXin.getCreateMenuUrl(), menuJson, params);
		//save log
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token(params.get("access_token").toString());
		log.setAction(Action.CREATE);
		log.setCust_id(config.getCust_id());
		log.setMsg(menuJson);
		log.setResult(result);
		log.setService_type(ServiceType.WEIXIN);
		mmtLogManager.saveCustomMenuLog(log);
		//parser result
		JSONObject json = JSONObject.fromObject(result);
		return (ReturnCode) JSONObject.toBean(json, ReturnCode.class);
	}

	@Override
	public String getMenus(MMTConfig config) {
		// TODO 获取微信自定义菜单【暂时可以不实现】
		return null;
	}

	@Override
	public ReturnCode deleteMenus(MMTConfig config) {
		WeiXinConfig cfg = (WeiXinConfig) config;
		if(cfg == null)
			throw new WeiXinException("客户微信配置信息不存在。");
		if(cfg.getAccount_type().equals(AccountType.DY))
			throw new WeiXinException("订阅号不支持自定义菜单操作。");
		//发送请求
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		String result = HttpConnector.post(WeiXin.getDeleteMenuUrl(), params);
		// save log
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token(params.get("access_token").toString());
		log.setAction(Action.CREATE);
		log.setCust_id(config.getCust_id());
		log.setMsg("");
		log.setResult(result);
		log.setService_type(ServiceType.WEIXIN);
		mmtLogManager.saveCustomMenuLog(log);
		JSONObject json = JSONObject.fromObject(result);
		return (ReturnCode) JSONObject.toBean(json, ReturnCode.class);
	}

	@Override
	public String getCommonUrl() {
		return WeiXin.getCommonUrl();
	}

	@Override
	public Map<String, Object> getAccessTokenRequestParams(MMTConfig config) {
		WeiXinConfig cfg = (WeiXinConfig) config;
		// 请求参数
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", cfg.getAppid());
		params.put("secret", cfg.getSecret());
		return params;
	}

	@Override
	public void sendError(String errorMsg) {
		throw new WeiXinException(errorMsg);
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.WEIXIN;
	}
	
	/**
	 * save revive message log
	 * @param cust_id
	 * @param msg
	 */
	private void saveReciveMessageLog(int cust_id, String msg){
		MsgLog log = MsgLog.createReciveLog(cust_id, msg);
		wxLogManager.saveMessageLog(log);
	}
	
	/**
	 * save send message log
	 * @param cust_id
	 * @param msg
	 */
	private void saveSendMessageLog(int cust_id, String msg){
		MsgLog log = MsgLog.createSendLog(cust_id, msg);
		wxLogManager.saveMessageLog(log);
	}

}
