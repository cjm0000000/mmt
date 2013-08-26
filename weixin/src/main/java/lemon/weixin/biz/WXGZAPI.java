package lemon.weixin.biz;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.api.MmtAPI;
import lemon.shared.common.MsgParser;
import lemon.shared.weixin.bean.MsgLog;
import lemon.shared.weixin.bean.SiteAccessLog;
import lemon.shared.weixin.dao.WXLogManager;
import lemon.weixin.util.WXHelper;

/**
 * The Weixin API for message
 * 
 * @author lemon
 * 
 */
@Service
public class WXGZAPI implements MmtAPI {
	private static Log logger = LogFactory.getLog(WXGZAPI.class);
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
		list.add(log.getTimestamp());
		list.add(log.getEchostr());
		list.add(log.getNonce());
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
		}
		// sha1 for signature
		String sh1str = WXHelper.sha1(log.getSignature());
		// compare
		return sb.toString().equalsIgnoreCase(sh1str);
	}

	@Override
	public String processMsg(String msg) {
		//save received log
		saveReciveMessageLog(msg);
		//get message type
		String msgType = getMsgType(msg);
		//get message parser
		parser = WXMsgParser.getParser(msgType);
		//process message and generate replay message
		String rMsg = parser.parseMessage(msg);
		//save log
		if(rMsg == null)
			saveSendMessageLog(rMsg);
		//replay weixin message
		return rMsg;
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
	 * @param msg
	 */
	private void saveReciveMessageLog(String msg){
		MsgLog log = MsgLog.createReciveLog(msg);
		wxLogManager.saveMessageLog(log);
	}
	
	/**
	 * save send message log
	 * @param msg
	 */
	private void saveSendMessageLog(String msg){
		MsgLog log = MsgLog.createSendLog(msg);
		wxLogManager.saveMessageLog(log);
	}


	/**
	 * Get message type
	 * @param msg
	 * @return
	 */
	private String getMsgType(String msg) {
		InputStream is = null;
		try {
			try {
				Document doc = new SAXBuilder().build(is);
				Element e = doc.getRootElement().getChild("MsgType");
				return e.getValue();
			} finally {
				if (null != is)
					is.close();
			}
		} catch (IOException | JDOMException e) {
			logger.error("can't get message type:" + e.getMessage());
		}
		return null;
	}

}
