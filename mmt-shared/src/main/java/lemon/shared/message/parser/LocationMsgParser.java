package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.recv.LocationMessage;

/**
 * A location message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.LOCATION)
public final class LocationMsgParser extends AbstractMsgParser {

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
