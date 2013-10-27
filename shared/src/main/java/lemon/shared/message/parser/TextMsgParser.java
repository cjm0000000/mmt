package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.TextMessage;

/**
 * A text message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.TEXT)
public final class TextMsgParser extends AbstractMsgParser {

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
