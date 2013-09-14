package lemon.weixin.biz.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.thoughtworks.xstream.XStream;

import lemon.shared.MMTContext;
import lemon.shared.common.Message;
import lemon.shared.common.MsgParser;
import lemon.shared.xstream.XStreamHelper;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.WeiXinMessage;
import lemon.weixin.biz.WeiXinException;
import lemon.weixin.biz.customer.WXCustMsgProcessor;

/**
 * A message parser for WeiXin
 * @author lemon
 * @version 1.0
 *
 */
public abstract class WXMsgParser implements MsgParser {
	public static final String PREFIX = "WX_";
	private static Log logger = LogFactory.getLog(WXMsgParser.class);
	protected XStream xStream = XStreamHelper.createXstream();
	
	/**
	 * Get message parser
	 * @param msg
	 * @return
	 */
	public static MsgParser getParser(String msg){
		String msgType = getMsgType(msg);
		return (MsgParser) MMTContext.getApplicationContext().getBean(PREFIX + msgType);
	}
	
	/**
	 * Generate replay message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public final String parseMessage(String mmt_token, String msg){
		WeiXinMessage message = toMsg(msg);
		// process business
		String retMsg = process(mmt_token, message);
		// build replay message
		return retMsg;
	}
	
	/**
	 * Convert string to message 
	 * @param msg
	 * @return
	 */
	protected abstract WeiXinMessage toMsg(String msg);
	
	/**
	 * Convert WeiXin message to XML
	 * @param rMsg
	 * @return
	 */
	protected abstract String toXML(Message rMsg);
	
	
	/**
	 * Business process
	 * @param mmt_token
	 * @param msg
	 * @return an string message
	 */
	private String process(String mmt_token, WeiXinMessage msg){
		WXCustMsgProcessor biz = null;
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
		if(null == cfg)
			throw new WeiXinException("No customer's configure find.");
		try {
			biz = (WXCustMsgProcessor) MMTContext.getApplicationContext().getBean(Class.forName(cfg.getBiz_class()));
			return biz.processBiz(mmt_token, msg);
		} catch (ClassNotFoundException e) {
			logger.error("Can't find business implement class: " + cfg.getBiz_class());
			throw new WeiXinException("Can't find business implement class: " + cfg.getBiz_class());
		}
	}
	
	/**
	 * Get message type
	 * @param msg
	 * @return
	 */
	private static String getMsgType(String msg) {
		InputStream is = null;
		try {
			try {
				is = new ByteArrayInputStream(msg.getBytes(WeiXin.LOCAL_CHARSET));
				Document doc = new SAXBuilder().build(is);
				Element e = doc.getRootElement().getChild("MsgType");
				return e.getValue();
			} finally {
				if (null != is)
					is.close();
			}
		} catch (IOException | JDOMException e) {
			logger.error("Can't get message type:" + e.getMessage());
		}
		return null;
	}
}
