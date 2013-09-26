package lemon.yixin.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.api.MmtAPI;
import lemon.shared.common.MsgParser;
import lemon.shared.request.bean.ReturnCode;
import lemon.shared.request.bean.Token;
import lemon.shared.util.HttpConnector;
import lemon.shared.util.SecureUtil;
import lemon.shared.xstream.XStreamHelper;
import lemon.yixin.YiXin;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.bean.log.MsgLog;
import lemon.yixin.bean.log.SiteAccessLog;
import lemon.yixin.biz.parser.YXMsgParser;
import lemon.yixin.dao.YXLogManager;

/**
 * The YiXin API for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("yiXinAPI")
public class YiXinAPI implements MmtAPI {
	private static Log logger = LogFactory.getLog(YiXinAPI.class);
	@Autowired
	private YXLogManager yxLogManager;
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
	
	/**
	 *  @param url the request URL, such as: <BR>
	 * 		https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * 		you can get appid and appsecret from {@link lemon.weixin.bean.WeiXinConfig WeiXinConfig}
	 */
	@Override
	public String getAcessToken(String mmt_token) {
		String url = YiXin.getCommonUrl();
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		
		// 请求参数
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", cfg.getAppid());
		params.put("secret", cfg.getSecret());
		String result = HttpConnector.get(url, params);
		if(result.startsWith("{\"errcode\"")){
			ReturnCode rCode = (ReturnCode) XStreamHelper.createJSONXStream()
					.fromXML(addRoot(ReturnCode.class, result));
			throw new YiXinException(rCode.getErrmsg());
		}
		Token token = (Token) XStreamHelper.createJSONXStream().fromXML(addRoot(Token.class, result));
		return token.getAccess_token();
	}

	/**
	 * save access log
	 * @param log
	 */
	private void saveAccessLog(SiteAccessLog log) {
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
	
	/**
	 * 为JSON字符串加根节点
	 * @param clazz
	 * @param json
	 * @return
	 */
	private String addRoot(Class<?> clazz, String json){
		StringBuilder sb = new StringBuilder();
		sb.append("{").append("\"");
		sb.append(clazz.getName()).append("\"").append(":");
		sb.append(json).append("}");
		return sb.toString();
	}

}
