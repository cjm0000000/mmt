package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.yixin.bean.message.MsgType;
import lemon.yixin.bean.message.VoiceMessage;

/**
 * A voice message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.VOICE)
public final class VoiceMsgParser extends YXMsgParser {

	@Override
	public final VoiceMessage toMsg(String msg) {
		xStream.processAnnotations(VoiceMessage.class);
		return (VoiceMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(VoiceMessage.class);
		return xStream.toXML(rMsg);
	}
}
