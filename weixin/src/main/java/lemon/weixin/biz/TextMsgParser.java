package lemon.weixin.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.util.WXHelper;

/**
 * A text message parser
 * 
 * @author lemon
 * 
 */
public class TextMsgParser extends WXMsgParser {
	private static Log logger = LogFactory.getLog(TextMsgParser.class);
	private XStream xStream = WXHelper.createXstream();

	@Override
	public String parseMessage(String msg) {
		TextMessage txt = toMsg(msg);
		// process business
		String retMsg = process(txt);
		logger.debug("Generate replay message:" + retMsg);
		// build replay message
		TextMessage rMsg = (TextMessage) txt.cloneMsg();
		rMsg.setContent(retMsg);
		return msg2xml(rMsg);
	}

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	private TextMessage toMsg(String msg) {
		xStream.processAnnotations(TextMessage.class);
		return (TextMessage) xStream.fromXML(msg);
	}

	/**
	 * Convert message to XML
	 * 
	 * @param rMsg
	 * @return
	 */
	private String msg2xml(Message rMsg) {
		xStream.processAnnotations(TextMessage.class);
		return xStream.toXML(rMsg);
	}

	/**
	 * process business
	 * 
	 * @param txt
	 * @return
	 */
	private String process(TextMessage txt) {
		// TODO process business
		// Test business
		return "This is a test biz.";
	}

}
