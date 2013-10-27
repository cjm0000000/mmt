package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.event.EventMessage;

/**
 * An event message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.EVENT)
public final class EventMsgParser extends AbstractMsgParser {

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
