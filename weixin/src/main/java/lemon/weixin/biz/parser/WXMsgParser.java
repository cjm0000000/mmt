package lemon.weixin.biz.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lemon.shared.common.Message;
import lemon.shared.common.MsgParser;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.MsgType;
import lemon.weixin.biz.WeiXinException;
import lemon.weixin.biz.customer.CustMsgBiz;

/**
 * A message parser for WeiXin
 * @author lemon
 *
 */
public abstract class WXMsgParser implements MsgParser {
	private static Map<String, MsgParser> parsers;
	private static Log logger = LogFactory.getLog(WXMsgParser.class);
	
	static{
		initParserInstances();
	}
	
	public static MsgParser getParser(String msgType){
		return parsers.get(msgType);
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
	 * Generate replay message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public final String parseMessage(String mmt_token, String msg){
		Message message = toMsg(msg);
		// process business
		String retMsg = process(mmt_token, message);
		// build replay message
		return retMsg;
	}
	
	/**
	 * Business process
	 * @param mmt_token
	 * @param msg
	 * @return an string message
	 */
	private String process(String mmt_token, Message msg){
		CustMsgBiz biz = null;
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
		if(null == cfg)
			throw new WeiXinException("No customer's configure find.");
		try {
			biz = (CustMsgBiz) Class.forName(cfg.getBiz_class()).newInstance();
			return biz.processBiz(mmt_token, msg);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			logger.error("Can't find business implement class.");
			throw new WeiXinException("Can't find business implement class: "+cfg.getBiz_class());
		}
	}
	
	/**
	 * Initialize parser instances
	 */
	private static void initParserInstances() {
		if (null == parsers) {
			parsers = new HashMap<>(8, 1F);
			parsers.put(MsgType.EVENT, new EventMsgParser());
			parsers.put(MsgType.IMAGE, new ImageMsgParser());
			parsers.put(MsgType.LINK, new LinkMsgParser());
			parsers.put(MsgType.LOCATION, new LocationMsgParser());
			parsers.put(MsgType.MUSIC, new MusicMsgParser());
			parsers.put(MsgType.NEWS, new NewsMsgParser());
			parsers.put(MsgType.TEXT, new TextMsgParser());
		}
	}
}
