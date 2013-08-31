package lemon.weixin.biz.customer;

import lemon.shared.common.Message;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.EventType;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.WeiXinMessage;
import lemon.weixin.biz.WeiXinException;
import lemon.weixin.biz.parser.TextMsgParser;

/**
 * Basic customer message processor
 * @author lemon
 *
 */
public abstract class CustBasicMsgProcessor implements CustMsgProcessor {
	
	public final String processBiz(String mmt_token, Message msg) {
		if(msg == null)
			throw new WeiXinException("receive a null message.");
		if(msg instanceof EventMessage){
			return processEvent(mmt_token,(EventMessage) msg);
		}else if(msg instanceof ImageMessage)
			return processImageMsg(mmt_token,(ImageMessage) msg);
		else if(msg instanceof LinkMessage)
			return processLinkMsg(mmt_token,(LinkMessage) msg);
		else if(msg instanceof LocationMessage)
			return processLocationMsg(mmt_token,(LocationMessage) msg);
		else if(msg instanceof MusicMessage)
			return processMusicMsg(mmt_token,(MusicMessage) msg);
		else if(msg instanceof NewsMessage)
			return processNewsMsg(mmt_token,(NewsMessage) msg);
		else if(msg instanceof TextMessage)
			return processTextMsg(mmt_token,(TextMessage) msg);
		return "Welcome to MMT message robot.";
	}
	
	/**
	 * process event message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public final String processEvent(String mmt_token, EventMessage msg){
		String eventType = msg.getEventType();
		if(null == eventType)
			throw new WeiXinException("Incorrect event type.");
		if(EventType.SUBSCRIBE.equalsIgnoreCase(eventType)) //process subscribe event
			return subscribe(mmt_token, msg);
		else if(EventType.UNSUBSCRIBE.equalsIgnoreCase(eventType))//process unsubscribe event
			return unsubscribe(mmt_token, msg);
		else if (EventType.CLICK.equalsIgnoreCase(eventType)) //process CLICK event
			return processClickEvent(mmt_token, msg);
		return null;
	}
	
	/**
	 * process image message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processImageMsg(String mmt_token, ImageMessage msg);
	
	
	/**
	 * process link message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processLinkMsg(String mmt_token, LinkMessage msg);
	
	/**
	 * process location message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processLocationMsg(String mmt_token, LocationMessage msg);
	
	/**
	 * process music message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processMusicMsg(String mmt_token, MusicMessage msg);
	
	/**
	 * process news message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processNewsMsg(String mmt_token, NewsMessage msg);
	
	/**
	 * process text message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processTextMsg(String mmt_token, TextMessage msg);
	
	/**
	 * process CLICK event<BR>
	 * you should implement in the subclass
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processClickEvent(String mmt_token, EventMessage msg);
	
	/**
	 * process subscribe event<BR>
	 * default implement send a welcome text message<BR>
	 * you can overwrite this method in the subclass
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	protected String subscribe(String mmt_token, EventMessage msg){
		//get customer's configure
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(cfg.getSubscribe_msg());
		return new TextMsgParser().toXML(replyMsg);
	}
	
	/**
	 * process unsubscribe event<BR>
	 * default implement send a thanks text message<BR>
	 * you can overwrite this method in the subclass
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	protected String unsubscribe(String mmt_token, EventMessage msg){
		//get customer's configure
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(cfg.getUnsubscribe_msg());
		return new TextMsgParser().toXML(replyMsg);
	}
	
	/**
	 * Build replay message<BR>
	 * set values for nodes: ToUserName/FromUserName/CreateTime/MsgId
	 * @param recvMsg
	 * @param replyMsg
	 */
	protected void buildReplyMsg(WeiXinMessage recvMsg, WeiXinMessage replyMsg){
		replyMsg.setToUserName(recvMsg.getFromUserName());
		replyMsg.setFromUserName(recvMsg.getToUserName());
		replyMsg.setCreateTime(System.currentTimeMillis());
		replyMsg.setMsgId(null);
	}
}
