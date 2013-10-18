package lemon.weixin.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.weixin.message.bean.ImageMessage;
import lemon.weixin.message.bean.MsgType;

/**
 * An image message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.IMAGE)
public final class ImageMsgParser extends WXMsgParser {

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
