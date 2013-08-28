package lemon.weixin.biz.parser;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.util.WXHelper;

/**
 * An event message parser
 * 
 * @author lemon
 * 
 */
public final class EventMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public final EventMessage toMsg(String msg) {
		xStream.processAnnotations(EventMessage.class);
		return (EventMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(EventMessage.class);
		return xStream.toXML(rMsg);
	}
}
