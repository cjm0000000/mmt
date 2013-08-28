package lemon.weixin.biz;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.util.WXHelper;

/**
 * A news message parser
 * 
 * @author lemon
 * 
 */
public final class NewsMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public final NewsMessage toMsg(String msg) {
		xStream.processAnnotations(NewsMessage.class);
		return (NewsMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(NewsMessage.class);
		return xStream.toXML(rMsg);
	}
}
