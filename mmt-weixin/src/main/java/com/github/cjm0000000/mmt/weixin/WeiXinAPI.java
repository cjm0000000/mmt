/*package com.github.cjm0000000.mmt.weixin;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;
import com.github.cjm0000000.mmt.weixin.config.AccountType;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;


*//**
 * The WeiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 *//*
@Service("weiXinAPI")
public final class WeiXinAPI extends AbstractMmtAPI {
	private static Log logger = LogFactory.getLog(WeiXinAPI.class);
	private MsgParser parser;

	@Override
	public String processMsg(String token, String msg) {
		WeiXinConfig cfg = WeiXin.getConfig(token);
		int cust_id = cfg.getCust_id();
		logger.debug("Receive a message: " + msg);
		//save received log
		saveRecvMsgLog(cust_id, msg);
		//get message parser
		parser = AbstractMsgParser.getParser(getServiceType(), msg);
		if(null == parser)
			throw new WeiXinException("No parser find.");
		//process message and generate replay message
		String rMsg = parser.parseMessage(cfg, msg);
		logger.debug("Generate a reply message: " + rMsg);
		//save reply log
		if(rMsg != null)
			saveSendMessageLog(cust_id, rMsg);
		//replay WeiXin message
		return rMsg;
	}
	
	@Override
	public String getMenus(MmtConfig config) {
		// TODO 获取微信自定义菜单【暂时可以不实现】
		return null;
	}

	@Override
	public String getCommonUrl() {
		return WeiXin.getCommonUrl();
	}
	
	@Override
	public String getCreateMenuUrl() {
		return WeiXin.getCreateMenuUrl();
	}

	@Override
	public String getDeleteMenuUrl() {
		return WeiXin.getDeleteMenuUrl();
	}
	
	@Override
	public String getCustomMsgUrl() {
		return WeiXin.getCustomMsgUrl();
	}

	@Override
	public Map<String, Object> getAccessTokenRequestParams(MmtConfig config) {
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

	@Override
	public void verifyConfig(MmtConfig config) {
		WeiXinConfig cfg = (WeiXinConfig) config;
		if(cfg == null)
			sendError("客户微信配置信息不存在。");
		if(cfg.getAccount_type().equals(AccountType.DY))
			sendError("订阅号不支持自定义菜单操作。");
	}

	@Override
	public String uploadMedia(MmtConfig config, String type, byte[] file, String fileName) {
		// 请求参数
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAcessToken(config));
		params.put("type", type);
		//上传
		String result = HttpConnector.uploadFile(WeiXin.getUploadMediaUrl(), params, file, fileName);
		logger.debug(result);
		return result;
	}

	@Override
	public JSONObject generateMsgDetail(Message msg) {
		JSONObject detail = new JSONObject();
		switch (msg.getMsgType()) {
		case MsgType.TEXT:
			TextMessage txtMsg = (TextMessage) msg;
			detail.put("content", txtMsg.getContent());
			break;
		case MsgType.IMAGE:
		case MsgType.VOICE:
		case MsgType.VIDEO:
			MediaMessage imgMsg = (MediaMessage) msg;
			detail.put("media_id", imgMsg.getMediaId());
			if(MsgType.VIDEO.equals(msg.getMsgType())){
				WXVideoMessage videoMsg = (WXVideoMessage) msg;
				detail.put("thumb_media_id", videoMsg.getThumbMediaId());
			}
			break;
		case MsgType.MUSIC:
			break;
		default:
			break;
		}
		return detail;
	}
	
}
*/