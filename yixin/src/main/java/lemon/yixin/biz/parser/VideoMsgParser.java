package lemon.yixin.biz.parser;

import org.springframework.stereotype.Service;

import lemon.shared.api.Message;
import lemon.yixin.bean.message.MsgType;
import lemon.yixin.bean.message.VideoMessage;

/**
 * A video message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service(YXMsgParser.PREFIX + MsgType.VIDEO)
public final class VideoMsgParser extends YXMsgParser {

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
