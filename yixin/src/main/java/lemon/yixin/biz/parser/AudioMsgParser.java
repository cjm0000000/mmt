package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.yixin.bean.message.MsgType;
import lemon.yixin.bean.message.AudioMessage;

/**
 * A voice message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.AUDIO)
public final class AudioMsgParser extends YXMsgParser {

	@Override
	public final AudioMessage toMsg(String msg) {
		xStream.processAnnotations(AudioMessage.class);
		return (AudioMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(AudioMessage.class);
		return xStream.toXML(rMsg);
	}
}
