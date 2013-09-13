package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.MusicMessage;

/**
 * An music message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.MUSIC)
public final class MusicMsgParser extends WXMsgParser {

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
