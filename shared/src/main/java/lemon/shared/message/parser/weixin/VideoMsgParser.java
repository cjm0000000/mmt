package lemon.shared.message.parser.weixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * A video message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("WEIXIN_" + MsgType.VIDEO)
public final class VideoMsgParser extends AbstractMsgParser {

	@Override
	public final WXVideoMessage toMsg(String msg) {
		xStream.processAnnotations(WXVideoMessage.class);
		return (WXVideoMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(WXVideoMessage.class);
		return xStream.toXML(rMsg);
	}
}
