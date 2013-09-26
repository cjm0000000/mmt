package lemon.yixin.biz.customer;

import org.springframework.beans.factory.annotation.Autowired;

import lemon.yixin.YiXin;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.bean.YiXinFans;
import lemon.yixin.bean.log.SubscribeLog;
import lemon.yixin.bean.log.UnSubscribeLog;
import lemon.yixin.bean.message.*;
import lemon.yixin.biz.YiXinException;
import lemon.yixin.biz.YiXinFansManager;
import lemon.yixin.biz.YiXinMsgHelper;
import lemon.yixin.biz.parser.TextMsgParser;
import lemon.yixin.dao.YXLogManager;

/**
 * Basic customer message processor
 * @author lemon
 * @version 1.0
 *
 */
public abstract class YXCustBasicMsgProcessor implements YXCustMsgProcessor {
	@Autowired
	private YiXinMsgHelper wxMsgHelper;
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private YXLogManager wxLogManager;
	@Autowired
	private YiXinFansManager wxFansManager;
	
	public final String processBiz(String mmt_token, YiXinMessage msg) {
		if(msg == null)
			throw new YiXinException("receive a null message.");
		//save receive message
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		msg.setCust_id(cfg.getCust_id());
		if(msg instanceof EventMessage){
			//save event message
			wxMsgHelper.saveRecvEventMsg((EventMessage) msg);
			return processEvent(mmt_token,(EventMessage) msg);
		}else if(msg instanceof ImageMessage){
			//save image message
			wxMsgHelper.saveRecvImageMsg((ImageMessage) msg);
			return processImageMsg(mmt_token,(ImageMessage) msg);
		}else if(msg instanceof LinkMessage){
			//save link message
			wxMsgHelper.saveRecvLinkMsg((LinkMessage) msg);
			return processLinkMsg(mmt_token,(LinkMessage) msg);
		}else if(msg instanceof LocationMessage){
			//save location message
			wxMsgHelper.saveRecvLocationMsg((LocationMessage) msg);
			return processLocationMsg(mmt_token,(LocationMessage) msg);
		}else if(msg instanceof TextMessage){
			//save text message
			wxMsgHelper.saveRecvTextMsg((TextMessage) msg);
			return processTextMsg(mmt_token,(TextMessage) msg);
		}else if(msg instanceof VideoMessage){
			//save video message
			wxMsgHelper.saveRecvVideoMsg((VideoMessage) msg);
			return processVideoMsg(mmt_token, (VideoMessage) msg);
		}else if(msg instanceof AudioMessage){
			//save voice message
			wxMsgHelper.saveRecvVoiceMsg((AudioMessage) msg);
			return processAudioMsg(mmt_token, (AudioMessage) msg);
		}else if(msg instanceof MusicMessage){
			//save music message
			wxMsgHelper.saveRecvMusicMsg((MusicMessage) msg);
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
		//save unsubscribe log
		UnSubscribeLog log = new UnSubscribeLog();
		log.setCust_id(cfg.getCust_id());
		log.setYxid(msg.getFromUserName());
		wxLogManager.saveUnSubscribeLog(log);
		//update fans information
		wxFansManager.disableFans(cfg.getCust_id(), msg.getFromUserName());
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
		//save subscribe log
		SubscribeLog log = new SubscribeLog();
		log.setCust_id(cfg.getCust_id());
		log.setYxid(msg.getFromUserName());
		wxLogManager.saveSubscribeLog(log);
		//save fans
		YiXinFans fans = new YiXinFans();
		fans.setCust_id(cfg.getCust_id());
		fans.setNick_name(null);
		fans.setYxid(msg.getFromUserName());
		wxFansManager.saveFans(fans);
		//process subscribe business
		return subscribe(cfg, msg);
	}
	
}
