package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.VoiceMessage;

/**
 * A voice message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.VOICE)
public final class VoiceMsgParser extends WXMsgParser {

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
