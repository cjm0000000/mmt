package com.github.cjm0000000.mmt.shared.message.processor;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.processor.PassiveMsgProcessor;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * passive message processor
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractPassiveMsgProcessor implements PassiveMsgProcessor {
	private static final Logger logger = Logger.getLogger(AbstractPassiveMsgProcessor.class);
	
	/**
	 * get MMT configure
	 * @param mmt_token
	 * @return
	 */
	protected abstract MmtConfig getConfig(String mmt_token);
	
	/**
	 * process event
	 * @param event
	 * @return
	 */
	protected abstract BaseMessage processEvent(SimpleEvent event);
	
	@Override
	public final BaseMessage process(String mmt_token, MmtService recvMsg) {
		MmtConfig cfg = getConfig(mmt_token);
		if(cfg == null)
			throw new MmtException("MMT config is null.");
		if(logger.isDebugEnabled())
			logger.debug("MMT configure is " + cfg);
		
		recvMsg.setCust_id(cfg.getCust_id());
		recvMsg.setService_type(getServiceType());
		
		if(recvMsg instanceof SimpleRecvMessage) // process passive message
			return processMessage((SimpleRecvMessage) recvMsg);
		
		if(recvMsg instanceof SimpleEvent) // process passive event
			return processEvent((SimpleEvent) recvMsg);
		
		throw new MmtException("No such message instance.", new ClassCastException());
	}
	
	/**
	 * 处理语音消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processAudioMsg(AudioMessage msg);
	
	/**
	 * 处理图片消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processImageMsg(ImageMessage msg);
	
	/**
	 * 处理链接消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processLinkMsg(LinkMessage msg);
	
	/**
	 * 处理地理位置消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processLocationMsg(LocationMessage msg);
	
	/**
	 * 处理文本消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processTextMsg(TextMessage msg);
	
	/**
	 * 处理视频消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processVideoMsg(IVideo msg);
	
	/**
	 * 处理语音消息
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processVoiceMsg(VoiceMessage msg);
	
	/**
	 * process message
	 * @param msg
	 * @return
	 */
	private final BaseMessage processMessage(SimpleRecvMessage msg){
		if(logger.isDebugEnabled())
			logger.debug("process message[msgType=" + msg.getMsgType() + "]");
		if(msg instanceof TextMessage)
			return processTextMsg((TextMessage) msg);
		if(msg instanceof AudioMessage)
			return processAudioMsg((AudioMessage) msg);
		if(msg instanceof ImageMessage)
			return processImageMsg((ImageMessage) msg);
		if(msg instanceof LinkMessage)
			return processLinkMsg((LinkMessage) msg);
		if(msg instanceof LocationMessage)
			return processLocationMsg((LocationMessage) msg);
		if(msg instanceof IVideo)
			return processVideoMsg((IVideo) msg);
		if(msg instanceof VoiceMessage)
			return processVoiceMsg((VoiceMessage) msg);
		logger.error("No message type found!!!");
		throw new MmtException("无法处理消息：[msgType=" + msg.getMsgType() + "]");
	}
}
