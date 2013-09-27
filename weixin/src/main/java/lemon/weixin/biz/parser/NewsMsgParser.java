package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.NewsMessage;

/**
 * A news message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.NEWS)
public final class NewsMsgParser extends WXMsgParser {

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
