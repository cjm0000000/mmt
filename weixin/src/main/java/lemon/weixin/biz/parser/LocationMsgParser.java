package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MsgType;

/**
 * A location message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.LOCATION)
public final class LocationMsgParser extends WXMsgParser {

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
