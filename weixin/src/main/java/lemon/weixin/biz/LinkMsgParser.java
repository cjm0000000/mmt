package lemon.weixin.biz;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.util.WXHelper;

/**
 * A link message parser
 * 
 * @author lemon
 * 
 */
public final class LinkMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public final LinkMessage toMsg(String msg) {
		xStream.processAnnotations(LinkMessage.class);
		return (LinkMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(LinkMessage.class);
		return xStream.toXML(rMsg);
	}
}
