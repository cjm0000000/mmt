package lemon.weixin.biz.parser;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.util.WXXStreamHelper;

/**
 * A news message parser
 * 
 * @author lemon
 * 
 */
@Service(MsgType.NEWS)
public final class NewsMsgParser extends WXMsgParser {
	private XStream xStream = WXXStreamHelper.createXstream();

	@Override
	public final NewsMessage toMsg(String msg) {
		xStream.processAnnotations(NewsMessage.class);
		return (NewsMessage) xStream.fromXML(msg);
	}

	@Override
	public final String toXML(Message rMsg) {
		xStream.processAnnotations(NewsMessage.class);
		return xStream.toXML(rMsg);
	}
}
