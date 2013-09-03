package lemon.weixin.biz.parser;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.util.WXXStreamHelper;

/**
 * An music message parser
 * 
 * @author lemon
 * 
 */
public final class MusicMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

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
