package lemon.weixin.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lemon.shared.common.Message;
import lemon.shared.common.MsgParser;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.biz.customer.CustomerReplyMsg;

/**
 * A message parser for WeiXin
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
		case MsgType.EVENT:
			return new EventMsgParser();
		case MsgType.IMAGE:
			return new ImageMsgParser();
		case MsgType.LINK:
			return new LinkMsgParser();
		case MsgType.LOCATION:
			return new LocationMsgParser();
		case MsgType.MUSIC:
			return new MusicMsgParser();
		case MsgType.NEWS:
			return new NewsMsgParser();
		default:
			return null;
		}
	}
	
	/**
	 * Convert string to message 
	 * @param msg
	 * @return
	 */
	protected abstract Message toMsg(String msg);
	
	/**
	 * Convert WeiXin message to XML
	 * @param rMsg
	 * @return
	 */
	protected abstract String toXML(Message rMsg);
	
	/**
	 * Business process
	 * @param txt
	 * @return an string message
	 */
	private String process(String token, Message msg){
		CustomerReplyMsg biz = null;
		WeiXinConfig cfg = WeiXin.getConfig(token);
		if(null == cfg)
			return null;
		try {
			biz = (CustomerReplyMsg) Class.forName(cfg.getBizClass()).newInstance();
			return biz.reply(msg);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			logger.error("Can't find business implement class.");
		}
		return null;
	}
	
	/**
	 * Generate replay message
	 * @param token
	 * @param msg
	 * @return
	 */
	public final String parseMessage(String token, String msg){
		Message message = toMsg(msg);
		// process business
		String retMsg = process(token, message);
		logger.debug("Generate replay message:" + retMsg);
		// build replay message
		return retMsg;
	}
}
