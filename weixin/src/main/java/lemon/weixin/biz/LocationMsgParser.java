package lemon.weixin.biz;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.util.WXHelper;

/**
 * A location message parser
 * 
 * @author lemon
 * 
 */
public final class LocationMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public final LocationMessage toMsg(String msg) {
		xStream.processAnnotations(LocationMessage.class);
		return (LocationMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(LocationMessage.class);
		return xStream.toXML(rMsg);
	}
}
