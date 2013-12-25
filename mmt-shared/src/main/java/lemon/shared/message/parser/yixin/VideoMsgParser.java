package lemon.shared.message.parser.yixin;

import org.springframework.stereotype.Service;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.message.parser.AbstractMsgParser;

/**
 * A video message parser
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("YIXIN_" + MsgType.VIDEO)
public final class VideoMsgParser extends AbstractMsgParser {

	@Override
	public final YXVideoMessage toMsg(String msg) {
		xStream.processAnnotations(YXVideoMessage.class);
		return (YXVideoMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(YXVideoMessage.class);
		return xStream.toXML(rMsg);
	}
}
