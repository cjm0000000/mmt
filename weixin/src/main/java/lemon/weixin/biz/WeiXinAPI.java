package lemon.weixin.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.api.AbstractMmtAPI;
import lemon.shared.common.MsgParser;
import lemon.shared.util.SecureUtil;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.log.MsgLog;
import lemon.weixin.bean.log.SiteAccessLog;
import lemon.weixin.biz.parser.WXMsgParser;
import lemon.weixin.dao.WXLogManager;

/**
 * The WeiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("weiXinAPI")
public class WeiXinAPI extends AbstractMmtAPI {
	private static Log logger = LogFactory.getLog(WeiXinAPI.class);
	@Autowired
	private WXLogManager wxLogManager;
	private MsgParser parser;

	@Override
	public boolean verifySignature(Map<String, Object> params) {
		SiteAccessLog log = (SiteAccessLog) params.get("SiteAccess");
		if (null == log || log.getSignature() == null)
			return false;
		// save log
		saveAccessLog(log);
		// nonce,token,timestamp dictionary sort
		List<String> list = new ArrayList<>();
		list.add(log.getNonce());
		list.add(log.getToken());
		list.add(log.getTimestamp());
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
		}
		// sha1 for signature
		String sha1str = SecureUtil.sha1(sb.toString());
		logger.debug("After SHA1:" + sha1str);
		// compare
		logger.debug("signature:" + log.getSignature());
		return sha1str.equalsIgnoreCase(log.getSignature());
	}

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
	public String createMenus(String mmt_token, String menuJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMenus(String mmt_token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMenus(String mmt_token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommonUrl() {
		return WeiXin.getCommonUrl();
	}

	@Override
	public final Map<String, Object> getAccessTokenRequestParams(String mmt_token) {
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
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

	/**
	 * save access log
	 * @param log
	 */
	private void saveAccessLog(SiteAccessLog log) {
		wxLogManager.saveSiteAccessLog(log);
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
