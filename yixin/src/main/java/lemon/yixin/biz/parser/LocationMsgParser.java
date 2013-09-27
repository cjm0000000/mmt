package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.yixin.bean.message.LocationMessage;
import lemon.yixin.bean.message.MsgType;

/**
 * A location message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.LOCATION)
public final class LocationMsgParser extends YXMsgParser {

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
