package lemon.shared.message.parser.yixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * An music message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("YIXIN_" + MsgType.MUSIC)
public final class MusicMsgParser extends AbstractMsgParser {

	@Override
	public final YXMusicMessage toMsg(String msg) {
		xStream.processAnnotations(YXMusicMessage.class);
		return (YXMusicMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(YXMusicMessage.class);
		return xStream.toXML(rMsg);
	}
}
