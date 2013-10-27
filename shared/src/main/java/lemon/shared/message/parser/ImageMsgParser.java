package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.recv.ImageMessage;

/**
 * An image message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.IMAGE)
public final class ImageMsgParser extends AbstractMsgParser {

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
