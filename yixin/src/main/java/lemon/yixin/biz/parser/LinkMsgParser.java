package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.yixin.bean.message.LinkMessage;
import lemon.yixin.bean.message.MsgType;

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
