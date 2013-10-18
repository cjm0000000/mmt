package lemon.weixin.message.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.weixin.message.bean.MsgType;
import lemon.weixin.message.bean.VideoMessage;

/**
 * A video message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(WXMsgParser.PREFIX + MsgType.VIDEO)
public final class VideoMsgParser extends WXMsgParser {

	@Override
	public final VideoMessage toMsg(String msg) {
		xStream.processAnnotations(VideoMessage.class);
		return (VideoMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(VideoMessage.class);
		return xStream.toXML(rMsg);
	}
}
