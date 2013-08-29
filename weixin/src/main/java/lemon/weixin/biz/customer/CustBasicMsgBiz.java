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
 * Basic message processor
 * @author lemon
 *
 */
public abstract class CustBasicMsgBiz implements CustMsgBiz {
	
	public final String processBiz(String token, Message msg) {
		if(msg == null)
			throw new WeiXinException("receive a null message.");
		if(msg instanceof EventMessage){
			return processEvent(token,(EventMessage) msg);
		}else if(msg instanceof ImageMessage)
			return processImageMsg(token,(ImageMessage) msg);
		else if(msg instanceof LinkMessage)
			return processLinkMsg(token,(LinkMessage) msg);
		else if(msg instanceof LocationMessage)
			return processLocationMsg(token,(LocationMessage) msg);
		else if(msg instanceof MusicMessage)
			return processMusicMsg(token,(MusicMessage) msg);
		else if(msg instanceof NewsMessage)
			return processNewsMsg(token,(NewsMessage) msg);
		else if(msg instanceof TextMessage)
			return processTextMsg(token,(TextMessage) msg);
		return "Welcome to MMT message robot.";
	}
	
	/**
	 * process event message
	 * @param msg
	 * @return
	 */
	public final String processEvent(String token, EventMessage msg){
		String eventType = msg.getEventType();
		if(null == eventType)
			throw new WeiXinException("Incorrect event type.");
		if(EventType.SUBSCRIBE.equalsIgnoreCase(eventType)) //process subscribe event
			return subscribe(token, msg);
		else if(EventType.UNSUBSCRIBE.equalsIgnoreCase(eventType))//process unsubscribe event
			return unsubscribe(token, msg);
		else if (EventType.CLICK.equalsIgnoreCase(eventType)) //process CLICK event
			return processClickEvent(token, msg);
		return null;
	}
	
	/**
	 * process image message
	 * @param msg
	 * @return
	 */
	public abstract String processImageMsg(String token, ImageMessage msg);
	
	
	/**
	 * process link message
	 * @param msg
	 * @return
	 */
	public abstract String processLinkMsg(String token, LinkMessage msg);
	
	/**
	 * process location message
	 * @param msg
	 * @return
	 */
	public abstract String processLocationMsg(String token, LocationMessage msg);
	
	/**
	 * process music message
	 * @param msg
	 * @return
	 */
	public abstract String processMusicMsg(String token, MusicMessage msg);
	
	/**
	 * process news message
	 * @param msg
	 * @return
	 */
	public abstract String processNewsMsg(String token, NewsMessage msg);
	
	/**
	 * process text message
	 * @param msg
	 * @return
	 */
	public abstract String processTextMsg(String token, TextMessage msg);
	
	/**
	 * process CLICK event
	 * you should implement in the subclass
	 * @param msg
	 * @return
	 */
	public abstract String processClickEvent(String token, EventMessage msg);
	
	/**
	 * process subscribe event
	 * default implement send a welcome text message<BR>
	 * you can overwrite this method in the subclass
	 * @param msg
	 * @return
	 */
	protected String subscribe(String token, EventMessage msg){
		//get customer's configure
		WeiXinConfig cfg = WeiXin.getConfig(token);
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(cfg.getSubscribe_msg());
		return new TextMsgParser().toXML(replyMsg);
	}
	
	/**
	 * process unsubscribe event
	 * default implement send a thanks text message<BR>
	 * you can overwrite this method in the subclass
	 * @param msg
	 * @return
	 */
	protected String unsubscribe(String token, EventMessage msg){
		//get customer's configure
		WeiXinConfig cfg = WeiXin.getConfig(token);
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
