package lemon.shared.message.parser.weixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.weixin.WXMusicMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * An music message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("WEIXIN_" + MsgType.MUSIC)
public final class MusicMsgParser extends AbstractMsgParser {

	@Override
	public final WXMusicMessage toMsg(String msg) {
		xStream.processAnnotations(WXMusicMessage.class);
		return (WXMusicMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(WXMusicMessage.class);
		return xStream.toXML(rMsg);
	}
}
