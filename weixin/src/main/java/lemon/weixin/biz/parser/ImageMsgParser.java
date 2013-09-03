package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.util.WXXStreamHelper;

/**
 * An image message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.IMAGE)
public final class ImageMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

	@Override
	public final ImageMessage toMsg(String msg) {
		xStream.processAnnotations(ImageMessage.class);
		return (ImageMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(ImageMessage.class);
		return xStream.toXML(rMsg);
	}
}
