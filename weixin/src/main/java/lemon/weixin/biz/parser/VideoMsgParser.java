package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.util.WXXStreamHelper;

/**
 * A video message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.VIDEO)
public final class VideoMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

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
