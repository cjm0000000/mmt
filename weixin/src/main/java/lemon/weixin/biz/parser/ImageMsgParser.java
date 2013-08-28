package lemon.weixin.biz.parser;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.util.WXHelper;

/**
 * An image message parser
 * 
 * @author lemon
 * 
 */
public final class ImageMsgParser extends WXMsgParser {
	private XStream xStream = WXHelper.createXstream();

	/**
	 * Convert XML to Message
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public final ImageMessage toMsg(String msg) {
		xStream.processAnnotations(ImageMessage.class);
		return (ImageMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(ImageMessage.class);
		return xStream.toXML(rMsg);
	}
}
