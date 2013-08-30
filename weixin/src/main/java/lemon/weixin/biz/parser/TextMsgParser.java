package lemon.weixin.biz.parser;

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
public final class TextMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	@Override
	public final TextMessage toMsg(String msg) {
		xStream.processAnnotations(TextMessage.class);
		return (TextMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(TextMessage.class);
		return xStream.toXML(rMsg);
	}
}
