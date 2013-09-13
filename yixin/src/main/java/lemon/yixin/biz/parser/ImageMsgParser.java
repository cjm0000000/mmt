package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.yixin.bean.message.ImageMessage;
import lemon.yixin.bean.message.MsgType;

/**
 * An image message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.IMAGE)
public final class ImageMsgParser extends YXMsgParser {

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
