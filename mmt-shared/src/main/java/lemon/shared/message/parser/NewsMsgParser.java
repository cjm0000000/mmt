package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.send.NewsMessage;

/**
 * A news message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.NEWS)
public final class NewsMsgParser extends AbstractMsgParser {

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
