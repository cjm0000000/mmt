package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.MsgType;

/**
 * A link message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.LINK)
public final class LinkMsgParser extends WXMsgParser {

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
