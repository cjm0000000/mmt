package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.util.WXXStreamHelper;

/**
 * A location message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.LOCATION)
public final class LocationMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

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
