package lemon.weixin.biz.customer;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.EventType;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.biz.WeiXinException;

/**
 * Basic message processor
 * @author lemon
 *
 */
public abstract class CustBasicMsgBiz implements CustMsgBiz {
	
	public final String processBiz(Message msg) {
		if(msg == null)
			throw new WeiXinException("receive a null message.");
		if(msg instanceof EventMessage){
			return processEvent((EventMessage) msg);
		}else if(msg instanceof ImageMessage)
			return processImageMsg((ImageMessage) msg);
		else if(msg instanceof LinkMessage)
			return processLinkMsg((LinkMessage) msg);
		else if(msg instanceof LocationMessage)
			return processLocationMsg((LocationMessage) msg);
		else if(msg instanceof MusicMessage)
			return processMusicMsg((MusicMessage) msg);
		else if(msg instanceof NewsMessage)
			return processNewsMsg((NewsMessage) msg);
		else if(msg instanceof TextMessage)
			return processTextMsg((TextMessage) msg);
		return "Welcome to MMT message robot.";
	}
	
	/**
	 * process event message
	 * @param msg
	 * @return
	 */
	public final String processEvent(EventMessage msg){
		String eventType = msg.getEventType();
		if(null == eventType)
			throw new WeiXinException("Incorrect event type.");
		if(EventType.SUBSCRIBE.equalsIgnoreCase(eventType)) //process subscribe event
			return subscribe(msg);
		else if(EventType.UNSUBSCRIBE.equalsIgnoreCase(eventType))//process unsubscribe event
			return unsubscribe(msg);
		else if (EventType.CLICK.equalsIgnoreCase(eventType)) //process CLICK event
			return processClickEvent(msg);
		return null;
	}
	
	/**
	 * process image message
	 * @param msg
	 * @return
	 */
	public abstract String processImageMsg(ImageMessage msg);
	
	
	/**
	 * process link message
	 * @param msg
	 * @return
	 */
	public abstract String processLinkMsg(LinkMessage msg);
	
	/**
	 * process location message
	 * @param msg
	 * @return
	 */
	public abstract String processLocationMsg(LocationMessage msg);
	
	/**
	 * process music message
	 * @param msg
	 * @return
	 */
	public abstract String processMusicMsg(MusicMessage msg);
	
	/**
	 * process news message
	 * @param msg
	 * @return
	 */
	public abstract String processNewsMsg(NewsMessage msg);
	
	/**
	 * process text message
	 * @param msg
	 * @return
	 */
	public abstract String processTextMsg(TextMessage msg);
	
	/**
	 * process CLICK event
	 * you should implement in the subclass
	 * @param msg
	 * @return
	 */
	public abstract String processClickEvent(EventMessage msg);
	
	/**
	 * process subscribe event
	 * default implement send a welcome text message<BR>
	 * you can overwrite this method in the subclass
	 * @param msg
	 * @return
	 */
	protected String subscribe(EventMessage msg){
		//FIXME implement default subscribe logic, read message from database
		return "Welcome subscribe";
	}
	
	/**
	 * process unsubscribe event
	 * default implement send a thanks text message<BR>
	 * you can overwrite this method in the subclass
	 * @param msg
	 * @return
	 */
	protected String unsubscribe(EventMessage msg){
		//FIXME implement default unsubscribe logic, read message from database
		return "You unsubscribe, thanks.";
	}
}
