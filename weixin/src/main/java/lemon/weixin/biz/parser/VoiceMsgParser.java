package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.util.WXXStreamHelper;

/**
 * A voice message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.VOICE)
public final class VoiceMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

	@Override
	public final VoiceMessage toMsg(String msg) {
		xStream.processAnnotations(VoiceMessage.class);
		return (VoiceMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(VoiceMessage.class);
		return xStream.toXML(rMsg);
	}
}
