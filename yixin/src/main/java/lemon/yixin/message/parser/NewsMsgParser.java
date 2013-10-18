package lemon.yixin.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.yixin.message.bean.MsgType;
import lemon.yixin.message.bean.NewsMessage;

/**
 * A news message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.NEWS)
public final class NewsMsgParser extends YXMsgParser {

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
