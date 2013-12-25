package com.github.cjm0000000.mmt.shared.message.processor;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;

/**
 * passive message processor
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractPassiveMsgProcessor extends AbstractPassiveEventProcessor {
	private static final Logger logger = Logger.getLogger(AbstractPassiveMsgProcessor.class);
	
	/**
	 * process event
	 * @param event
	 * @return
	 */
	protected abstract BaseMessage processEvent(SimpleEvent event);
	
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
	public final BaseMessage processMessage(SimpleRecvMessage msg){
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
