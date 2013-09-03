package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.util.WXXStreamHelper;

/**
 * A text message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.TEXT)
public final class TextMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

	@Override
	public final TextMessage toMsg(String msg) {
		xStream.processAnnotations(TextMessage.class);
		return (TextMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(TextMessage.class);
		return xStream.toXML(rMsg);
	}
}
