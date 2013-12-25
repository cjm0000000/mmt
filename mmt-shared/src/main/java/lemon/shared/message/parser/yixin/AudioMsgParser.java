package lemon.shared.message.parser.yixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * A voice message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("YIXIN_" + MsgType.AUDIO)
public final class AudioMsgParser extends AbstractMsgParser {

	@Override
	public final YXAudioMessage toMsg(String msg) {
		xStream.processAnnotations(YXAudioMessage.class);
		return (YXAudioMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(YXAudioMessage.class);
		return xStream.toXML(rMsg);
	}
}
