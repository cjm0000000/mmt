package lemon.shared.message.processor;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.Status;

import lemon.shared.config.MMTConfig;
import lemon.shared.event.EventType;
import lemon.shared.fans.Fans;
import lemon.shared.fans.FansManager;
import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.AudioMessage;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.VideoMessage;
import lemon.shared.message.metadata.VoiceMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.MusicMessage;
import lemon.shared.message.parser.TextMsgParser;
import lemon.shared.message.processor.MsgProcessor;

/**
 * Basic message processor
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AbstractMsgProcessor implements MsgProcessor {
	@Autowired
	private FansManager fansManager;
	@Autowired
	private MsgManager msgManager;
	@Autowired
	private TextMsgParser textMsgParser;
	
	public final String processMsg(String mmt_token, Message msg) {
		if(msg == null)
			throw new MmtException("receive a null message.");
		//save receive message
		MMTConfig cfg = getConfig(mmt_token);
		msg.setCust_id(cfg.getCust_id());
		msg.setService_type(getServiceType());
		if(msg instanceof EventMessage){
			//save event message
			msgManager.saveRecvEventMsg((EventMessage) msg);
			return processEvent(mmt_token,(EventMessage) msg);
		}else if(msg instanceof ImageMessage){
			//save image message
			msgManager.saveRecvImageMsg((ImageMessage) msg);
			return processImageMsg(mmt_token,(ImageMessage) msg);
		}else if(msg instanceof LinkMessage){
			//save link message
			msgManager.saveRecvLinkMsg((LinkMessage) msg);
			return processLinkMsg(mmt_token,(LinkMessage) msg);
		}else if(msg instanceof LocationMessage){
			//save location message
			msgManager.saveRecvLocationMsg((LocationMessage) msg);
			return processLocationMsg(mmt_token,(LocationMessage) msg);
		}else if(msg instanceof TextMessage){
			//save text message
			msgManager.saveRecvTextMsg((TextMessage) msg);
			return processTextMsg(mmt_token,(TextMessage) msg);
		}else if(msg instanceof VideoMessage){
			//save video message
			msgManager.saveRecvVideoMsg((VideoMessage) msg);
			return processVideoMsg(mmt_token, (VideoMessage) msg);
		}else if(msg instanceof VoiceMessage){
			//save voice message
			msgManager.saveRecvVoiceMsg((VoiceMessage) msg);
			return processVoiceMsg(mmt_token, (VoiceMessage) msg);
		}else if(msg instanceof AudioMessage){
			//save audio message
			msgManager.saveRecvAudioMsg((AudioMessage)msg);
			return processAudioMsg(mmt_token, (AudioMessage)msg);
		}else if(msg instanceof MusicMessage){
			//save music message
			msgManager.saveRecvMusicMsg((MusicMessage)msg);
			return processMusicMsg(mmt_token, (MusicMessage)msg);
		}
		return null;
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
			throw new MmtException("Incorrect event type.");
		if(EventType.SUBSCRIBE.equalsIgnoreCase(eventType)) //process subscribe event
			return preSubscribe(mmt_token, msg);
		else if(EventType.UNSUBSCRIBE.equalsIgnoreCase(eventType))//process unsubscribe event
			return unsubscribe(mmt_token, msg);
		else if (EventType.CLICK.equalsIgnoreCase(eventType)) //process CLICK event
			return processClickEvent(mmt_token, msg);
		return null;
	}
	
	/**
	 * get MMT configure
	 * @param mmt_token
	 * @return
	 */
	public abstract MMTConfig getConfig(String mmt_token);
	
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
	 * process video event
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processVideoMsg(String mmt_token, VideoMessage msg);
	
	/**
	 * process voice message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processVoiceMsg(String mmt_token, VoiceMessage msg);
	
	/**
	 * process audio message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processAudioMsg(String mmt_token, AudioMessage msg);
	
	/**
	 * process music message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public abstract String processMusicMsg(String mmt_token, MusicMessage msg);
	
	/**
	 * process subscribe event<BR>
	 * default implement send a welcome text message<BR>
	 * you can overwrite this method in the subclass
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	protected String subscribe(MMTConfig cfg, EventMessage msg){
		//get customer's configure
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(cfg.getSubscribe_msg());
		return textMsgParser.toXML(replyMsg);
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
		MMTConfig cfg = getConfig(mmt_token);
		//update fans information
		fansManager.disableFans(cfg.getCust_id(), getServiceType(), msg.getFromUserName());
		return null;
	}
	
	/**
	 * Build replay message<BR>
	 * set values for nodes: ToUserName/FromUserName/CreateTime/MsgId
	 * @param recvMsg
	 * @param replyMsg
	 */
	protected void buildReplyMsg(Message recvMsg, Message replyMsg){
		replyMsg.setToUserName(recvMsg.getFromUserName());
		replyMsg.setFromUserName(recvMsg.getToUserName());
		replyMsg.setCreateTime((int) System.currentTimeMillis());
		replyMsg.setMsgId(null);
	}
	
	/**
	 * get welcome message
	 * @param cfg
	 * @return
	 */
	protected String getWelcome(MMTConfig cfg){
		return cfg.getWelcome_msg();
	}
	
	/**
	 * 发送文本消息
	 * @param msg
	 * @param content
	 * @return
	 */
	protected String sendTextMessage( Message msg,String content){
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(content);
		replyMsg.setService_type(getServiceType());
		// save log
		msgManager.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}
	
	/**
	 * save log before do subscribe
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	private final String preSubscribe(String mmt_token, EventMessage msg){
		MMTConfig cfg = getConfig(mmt_token);
		//save fans
		Fans fans = obtainFans(cfg, msg.getFromUserName());
		fansManager.saveFans(fans);
		//process subscribe business
		return subscribe(cfg, msg);
	}
	
	/**
	 * 组装Fan
	 * @param cfg
	 * @param user_id
	 * @return
	 */
	private Fans obtainFans(MMTConfig cfg, String user_id) {
		Fans fans = new Fans();
		fans.setCust_id(cfg.getCust_id());
		fans.setService_type(getServiceType());
		fans.setStatus(Status.AVAILABLE);
		fans.setNick_name("");
		fans.setUser_id(user_id);
		return fans;
	}
	
}
