package lemon.yixin.message.processor;

import org.springframework.beans.factory.annotation.Autowired;

import lemon.shared.entity.Status;
import lemon.shared.fans.Fans;
import lemon.shared.fans.FansManager;
import lemon.shared.service.ServiceType;
import lemon.yixin.YiXinException;
import lemon.yixin.config.YiXin;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.message.YiXinMsgHelper;
import lemon.yixin.message.bean.AudioMessage;
import lemon.yixin.message.bean.EventMessage;
import lemon.yixin.message.bean.EventType;
import lemon.yixin.message.bean.ImageMessage;
import lemon.yixin.message.bean.LinkMessage;
import lemon.yixin.message.bean.LocationMessage;
import lemon.yixin.message.bean.MusicMessage;
import lemon.yixin.message.bean.TextMessage;
import lemon.yixin.message.bean.VideoMessage;
import lemon.yixin.message.bean.YiXinMessage;
import lemon.yixin.message.parser.TextMsgParser;

/**
 * Basic customer message processor
 * @author lemon
 * @version 1.0
 *
 */
public abstract class YXBasicMsgProcessor implements YXMsgProcessor {
	@Autowired
	private YiXinMsgHelper yxMsgHelper;
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private FansManager fansManager;
	
	public final ServiceType getServiceType(){
		return ServiceType.WEIXIN;
	}
	
	public final String processBiz(String mmt_token, YiXinMessage msg) {
		if(msg == null)
			throw new YiXinException("receive a null message.");
		//save receive message
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		msg.setCust_id(cfg.getCust_id());
		if(msg instanceof EventMessage){
			//save event message
			yxMsgHelper.saveRecvEventMsg((EventMessage) msg);
			return processEvent(mmt_token,(EventMessage) msg);
		}else if(msg instanceof ImageMessage){
			//save image message
			yxMsgHelper.saveRecvImageMsg((ImageMessage) msg);
			return processImageMsg(mmt_token,(ImageMessage) msg);
		}else if(msg instanceof LinkMessage){
			//save link message
			yxMsgHelper.saveRecvLinkMsg((LinkMessage) msg);
			return processLinkMsg(mmt_token,(LinkMessage) msg);
		}else if(msg instanceof LocationMessage){
			//save location message
			yxMsgHelper.saveRecvLocationMsg((LocationMessage) msg);
			return processLocationMsg(mmt_token,(LocationMessage) msg);
		}else if(msg instanceof TextMessage){
			//save text message
			yxMsgHelper.saveRecvTextMsg((TextMessage) msg);
			return processTextMsg(mmt_token,(TextMessage) msg);
		}else if(msg instanceof VideoMessage){
			//save video message
			yxMsgHelper.saveRecvVideoMsg((VideoMessage) msg);
			return processVideoMsg(mmt_token, (VideoMessage) msg);
		}else if(msg instanceof AudioMessage){
			//save voice message
			yxMsgHelper.saveRecvVoiceMsg((AudioMessage) msg);
			return processAudioMsg(mmt_token, (AudioMessage) msg);
		}else if(msg instanceof MusicMessage){
			//save music message
			yxMsgHelper.saveRecvMusicMsg((MusicMessage) msg);
			return processMusicMsg(mmt_token, (MusicMessage) msg);
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
			throw new YiXinException("Incorrect event type.");
		if(EventType.SUBSCRIBE.equalsIgnoreCase(eventType)) //process subscribe event
			return preSubscribe(mmt_token, msg);
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
	protected String subscribe(YiXinConfig cfg, EventMessage msg){
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
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
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
	protected void buildReplyMsg(YiXinMessage recvMsg, YiXinMessage replyMsg){
		replyMsg.setToUserName(recvMsg.getFromUserName());
		replyMsg.setFromUserName(recvMsg.getToUserName());
		replyMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
		replyMsg.setMsgId(null);
	}
	
	/**
	 * get welcome message
	 * @param cfg
	 * @return
	 */
	protected String getWelcome(YiXinConfig cfg){
		return cfg.getWelcome_msg();
	}
	
	/**
	 * save log before do subscribe
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	private final String preSubscribe(String mmt_token, EventMessage msg){
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		// save fans
		Fans fans = obtainFans(cfg, msg.getFromUserName());
		fansManager.saveFans(fans);
		// process subscribe business
		return subscribe(cfg, msg);
	}
	
	/**
	 * 组装Fan
	 * @param cfg
	 * @param user_id
	 * @return
	 */
	private Fans obtainFans(YiXinConfig cfg, String user_id) {
		Fans fans = new Fans();
		fans.setCust_id(cfg.getCust_id());
		fans.setService_type(getServiceType());
		fans.setStatus(Status.AVAILABLE);
		fans.setNick_name("");
		fans.setUser_id(user_id);
		return fans;
	}
	
}
