package lemon.yixin;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lemon.shared.api.AbstractMmtAPI;
import lemon.shared.config.MMTConfig;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.parser.AbstractMsgParser;
import lemon.shared.message.parser.MsgParser;
import lemon.shared.service.ServiceType;
import lemon.yixin.config.YiXin;
import lemon.yixin.config.bean.YiXinConfig;

/**
 * The YiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("yiXinAPI")
public final class YiXinAPI extends AbstractMmtAPI {
	private static Log logger = LogFactory.getLog(YiXinAPI.class);
	private MsgParser parser;
	
	@Override
	public String processMsg(String token, String msg) {
		YiXinConfig cfg = YiXin.getConfig(token);
		int cust_id = cfg.getCust_id();
		logger.debug("Receive a message: " + msg);
		//save received log
		saveRecvMsgLog(cust_id, msg);
		//get message parser
		parser = AbstractMsgParser.getParser(getServiceType(), msg);
		if(null == parser)
			throw new YiXinException("No parser find.");
		//process message and generate replay message
		String rMsg = parser.parseMessage(cfg, msg);
		logger.debug("Generate a reply message: " + rMsg);
		//save reply log
		if(rMsg != null)
			saveSendMessageLog(cust_id, rMsg);
		//replay YiXin message
		return rMsg;
	}
	
	@Override
	public String getMenus(MMTConfig config) {
		// TODO 获取易信自定义菜单【暂时可以不实现】
		return null;
	}

	@Override
	public String getCreateMenuUrl() {
		return YiXin.getCreateMenuUrl();
	}

	@Override
	public String getDeleteMenuUrl() {
		return YiXin.getDeleteMenuUrl();
	}
	
	@Override
	public String getCommonUrl() {
		return YiXin.getCommonUrl();
	}
	
	@Override
	public String getCustomMsgUrl() {
		// TODO 易信客服消息预留接口
		return null;
	}

	@Override
	public Map<String, Object> getAccessTokenRequestParams(MMTConfig config) {
		YiXinConfig cfg = (YiXinConfig) config;
		// 请求参数
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", cfg.getAppid());
		params.put("secret", cfg.getSecret());
		return params;
	}

	@Override
	public void sendError(String errorMsg) {
		throw new YiXinException(errorMsg);
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.YIXIN;
	}

	@Override
	public void verifyConfig(MMTConfig config) {
		YiXinConfig cfg = (YiXinConfig) config;
		if (cfg == null)
			sendError("客户易信配置信息不存在。");
	}

	@Override
	public String uploadMedia(MMTConfig config, String type, byte[] file,
			String fileName) {
		//Nothing to do
		return null;
	}

	@Override
	public JSONObject generateMsgDetail(Message msg) {
		// 易信暂时不需要实现
		return null;
	}

}
