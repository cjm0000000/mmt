package lemon.yixin.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.yixin.message.bean.LinkMessage;
import lemon.yixin.message.bean.MsgType;

/**
 * A link message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.LINK)
public final class LinkMsgParser extends YXMsgParser {

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
