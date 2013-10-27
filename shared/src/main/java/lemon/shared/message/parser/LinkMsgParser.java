package lemon.shared.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.recv.LinkMessage;

/**
 * A link message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(AbstractMsgParser.PREFIX + MsgType.LINK)
public final class LinkMsgParser extends AbstractMsgParser {

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
