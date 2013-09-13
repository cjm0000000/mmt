package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.yixin.bean.message.MsgType;
import lemon.yixin.bean.message.MusicMessage;

/**
 * An music message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.MUSIC)
public final class MusicMsgParser extends YXMsgParser {

	@Override
	public final MusicMessage toMsg(String msg) {
		xStream.processAnnotations(MusicMessage.class);
		return (MusicMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(MusicMessage.class);
		return xStream.toXML(rMsg);
	}
}
