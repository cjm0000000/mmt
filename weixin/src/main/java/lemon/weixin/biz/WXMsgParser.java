package lemon.weixin.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lemon.shared.common.MsgParser;
import lemon.weixin.bean.message.MsgType;

/**
 * A message parser for Weixin
 * @author lemon
 *
 */
public abstract class WXMsgParser implements MsgParser {
	
	private static Log logger = LogFactory.getLog(WXMsgParser.class);
	
	public static MsgParser getParser(String msgType){
		if(null == msgType)
			return null;
		switch (msgType) {
		case MsgType.TEXT:
			return new TextMsgParser();
		//TODO add other message parser
		default:
			return null;
		}
	}

}
