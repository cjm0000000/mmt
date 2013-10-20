package lemon.yixin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.access.bean.SiteAccess;
import lemon.shared.api.MsgParser;
import lemon.shared.api.simple.AbstractMmtAPI;
import lemon.shared.api.simple.MMTConfig;
import lemon.shared.request.bean.ReturnCode;
import lemon.yixin.config.YiXin;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.log.bean.MsgLog;
import lemon.yixin.log.mapper.YXLogManager;
import lemon.yixin.message.parser.YXMsgParser;

/**
 * The YiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("yiXinAPI")
public class YiXinAPI extends AbstractMmtAPI {
	private static Log logger = LogFactory.getLog(YiXinAPI.class);
	@Autowired
	private YXLogManager yxLogManager;
	private MsgParser parser;

	@Override
	public String processMsg(String token, String msg) {
		YiXinConfig cfg = YiXin.getConfig(token);
		int cust_id = cfg.getCust_id();
		logger.debug("Receive a message: " + msg);
		//save received log
		saveReciveMessageLog(cust_id, msg);
		//get message parser
		parser = YXMsgParser.getParser(msg);
		if(null == parser)
			throw new YiXinException("No parser find.");
		//process message and generate replay message
		String rMsg = parser.parseMessage(token, msg);
		logger.debug("Generate a reply message: " + rMsg);
		//save reply log
		if(rMsg != null)
			saveSendMessageLog(cust_id, rMsg);
		//replay YiXin message
		return rMsg;
	}
	
	@Override
	public ReturnCode createMenus(MMTConfig config, String menuJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMenus(MMTConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode deleteMenus(MMTConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommonUrl() {
		return YiXin.getCommonUrl();
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
	
	/**
	 * save access log
	 * @param log
	 */
	@Override
	public final void saveAccessLog(SiteAccess log) {
		yxLogManager.saveSiteAccessLog(log);
	}
	
	/**
	 * save revive message log
	 * @param cust_id
	 * @param msg
	 */
	private void saveReciveMessageLog(int cust_id, String msg){
		MsgLog log = MsgLog.createReciveLog(cust_id, msg);
		yxLogManager.saveMessageLog(log);
	}
	
	/**
	 * save send message log
	 * @param cust_id
	 * @param msg
	 */
	private void saveSendMessageLog(int cust_id, String msg){
		MsgLog log = MsgLog.createSendLog(cust_id, msg);
		yxLogManager.saveMessageLog(log);
	}

}
