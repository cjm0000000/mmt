package lemon.shared.message.parser.weixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * A voice message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("WEIXIN_" + MsgType.VOICE)
public final class VoiceMsgParser extends AbstractMsgParser {

	@Override
	public final WXVoiceMessage toMsg(String msg) {
		xStream.processAnnotations(WXVoiceMessage.class);
		return (WXVoiceMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(WXVoiceMessage.class);
		return xStream.toXML(rMsg);
	}
}
