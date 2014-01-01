package com.github.cjm0000000.mmt.shared.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.KeyEvent;
import com.github.cjm0000000.mmt.core.message.event.LocationEvent;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;
import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.persistence.EventRepository;
import com.github.cjm0000000.mmt.shared.message.persistence.MsgRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;

/**
 * Message manager
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class MsgManager {
	@Autowired
	private MsgRepository msgRepository;
	@Autowired
	private EventRepository eventRepository;
	
	/**
	 * get receive subscribe event
	 * @param cust_id
	 * @param service_type
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SimpleEvent> getSubscribeList(int cust_id,
			ServiceType service_type, int start, int limit) {
		//TODO implement getSubscribeList
		return null;
	}
	
	/**
	 * get receive unsubscribe event
	 * @param cust_id
	 * @param service_type
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SimpleEvent> getUnsubscribeList(int cust_id,
			ServiceType service_type, int start, int limit) {
		//TODO implement getUnsubscribeList
		return null;
	}
	
	/**
	 * get receive scan event
	 * @param cust_id
	 * @param service_type
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ScanEvent> getScanList(int cust_id,
			ServiceType service_type, int start, int limit) {
		//TODO implement getScanList
		return null;
	}
	
	/**
	 * get receive location event
	 * @param cust_id
	 * @param service_type
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<LocationEvent> getLocationList(int cust_id,
			ServiceType service_type, int start, int limit) {
		//TODO implement getLocationList
		return null;
	}
	
	/**
	 * get receive CLICK event
	 * @param cust_id
	 * @param service_type
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<KeyEvent> getClickList(int cust_id,
			ServiceType service_type, int start, int limit) {
		//TODO implement getClickList
		return null;
	}
	
	/**
	 * get receive detail message
	 * @param cust_id
	 * @param service_type
	 * @param msgType
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<BaseMessage> getRecvMsgList(int cust_id, ServiceType service_type,
			String msgType, int start, int limit) {
		return msgRepository.getRecvMsgList(cust_id, service_type, msgType,
				start, limit);
	}
	
	/**
	 * get receive text message
	 * @param ids
	 * @return
	 */
	public List<TextMessage> getRecvTextMsgList(long[] ids){
		return msgRepository.getRecvTextMsgList(ids);
	}
	
	/**
	 * get receive detail message count
	 * @param cust_id
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	public int getRecvMsgCnt(int cust_id, ServiceType service_type,
			String msgType) {
		return msgRepository.getRecvMsgCnt(cust_id, service_type, msgType);
	}
	
	/**
	 * get receive message detail
	 * @param msgId
	 * @return
	 */
	public BaseMessage getRecvMsgDetail(long msgId){
		return msgRepository.getRecvMsgDetail(msgId);
	}
	
	/**
	 * get receive text message by message id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getRecvTextMsg(long msg_id){
		return msgRepository.getRecvTextMsg(msg_id);
	}
	
	/**
	 * get receive YiXin audio message by message id
	 * @param msg_id
	 * @return
	 */
	public AudioMessage getRecvYXAudioMsg(long msg_id){
		return msgRepository.getRecvAudioMsg(msg_id);
	}
	
	/**
	 * get receive image message by message id
	 * @param msg_id
	 * @return
	 */
	public ImageMessage getRecvImageMsg(long msg_id){
		return msgRepository.getRecvImageMsg(msg_id);
	}
	
	/**
	 * get receive link message by message id
	 * @param msg_id
	 * @return
	 */
	public LinkMessage getRecvLinkMsg(long msg_id){
		return msgRepository.getRecvLinkMsg(msg_id);
	}
	
	/**
	 * get receive location message by message id
	 * @param msg_id
	 * @return
	 */
	public LocationMessage getRecvLocationMsg(long msg_id){
		return msgRepository.getRecvLocationMsg(msg_id);
	}
	
	/**
	 * get receive WeiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage getRecvWXVideoMsg(long msg_id) {
		return msgRepository.getRecvWXVideoMsg(msg_id);
	}
	
	/**
	 * get receive YiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage getRecvYXVideoMsg(long msg_id){
		return msgRepository.getRecvYXVideoMsg(msg_id);
	}
	
	/**
	 * get receive YiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public VoiceMessage getRecvWXVoiceMsg(long msg_id){
		return msgRepository.getRecvVoiceMsg(msg_id);
	}
	
	/**
	 * get receive YiXin music message by message id
	 * @param msg_id
	 * @return
	 */
	public MusicMessage getRecvYXMusicMsg(long msg_id){
		return msgRepository.getRecvYXMusicMsg(msg_id);
	}
	
	/**
	 * get send news message by message id
	 * @param msg_id
	 * @return
	 */
	public NewsMessage getSendNewsMsg(long msg_id){
		return msgRepository.getSendNewsMsg(msg_id);
	}
	
	/**
	 * get send news articles by message id
	 * @param msg_id
	 * @return
	 */
	public List<NewsNode> getArticlesByNews(long msg_id){
		return msgRepository.getArticlesByNews(msg_id);
	}
	
	/**
	 * get send text message by message id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getSendTextMsg(long msg_id){
		return msgRepository.getSendTextMsg(msg_id);
	}
	
	/**
	 * get receive key event by event id
	 * @param eventId
	 * @return
	 */
	public KeyEvent getRecvKeyEvent(long eventId){
		return eventRepository.getRecvKeyEvent(eventId);
	}
	
	/**
	 * get receive location event by event id
	 * @param eventId
	 * @return
	 */
	public LocationEvent getRecvLocationEvent(long eventId){
		return eventRepository.getRecvLocationEvent(eventId);
	}
	
	/**
	 * get receive scan event by event id
	 * @param eventId
	 * @return
	 */
	public ScanEvent getRecvScanEvent(long eventId){
		return eventRepository.getRecvScanEvent(eventId);
	}
	
	/**
	 * get receive simple event by event id
	 * @param eventId
	 * @return
	 */
	public SimpleEvent getRecvSimpleEvent(long eventId){
		return eventRepository.getRecvSimpleEvent(eventId);
	}
	
	/**
	 * save received audio message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvAudioMsg(AudioMessage msg) {
		if (!(msg instanceof AudioMessage))
			throw new MmtException("不是YXAudioMessage类型，无法保存。");
		saveRecvMsg((AudioMessage) msg);
		msgRepository.saveRecvAudioMsg((AudioMessage)msg);
	}
	
	/**
	 * save received image message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvImageMsg(ImageMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvImageMsg(msg);
	}
	
	/**
	 * save received link message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvLinkMsg(LinkMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvLinkMsg(msg);
	}
	
	/**
	 * save received location message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvLocationMsg(LocationMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvLocationMsg(msg);
	}
	
	/**
	 * save received text message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvTextMsg(TextMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvTextMsg(msg);
	}
	
	/**
	 * save received video message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvVideoMsg(IVideo msg) {
		if (msg instanceof com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage) {
			com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage wxMsg = (com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage) msg;
			saveRecvMsg(wxMsg);
			msgRepository.saveRecvWXVideoMessage(wxMsg);
		} else if (msg instanceof com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage) {
			com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage yxMsg = (com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage) msg;
			saveRecvMsg(yxMsg);
			msgRepository.saveRecvYXVideoMessage(yxMsg);
		}
	}
	
	/**
	 * save received voice message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvVoiceMsg(VoiceMessage msg) {
		saveRecvMsg(msg);
		msgRepository.saveRecvVoiceMsg(msg);
	}
	
	/**
	 * save received music message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvMusicMsg(MusicMessage msg) {
		saveRecvMsg(msg);
		msgRepository.saveRecvYXMusicMsg(msg);
	}
	
	/**
	 * save receive key event
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvKeyEvent(KeyEvent event){
		saveRecvEvent(event);
		eventRepository.saveRecvKeyEvent(event);
	}
	
	/**
	 * save receive location event
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvLocationEvent(LocationEvent event){
		saveRecvEvent(event);
		eventRepository.saveRecvLocationEvent(event);
	}
	
	/**
	 * save receive scan event
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvScanEvent(ScanEvent event){
		saveRecvEvent(event);
		eventRepository.saveRecvScanEvent(event);
	}
	
	/**
	 * save receive simple event
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRecvSimpleEvent(SimpleEvent event){
		saveRecvEvent(event);
		eventRepository.saveRecvSimpleEvent(event);
	}
	
	/**
	 * save send music message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSendNewsMsg(NewsMessage msg){
		saveSendMsg(msg);
		msgRepository.saveSendNewsMsg(msg);
		for (NewsNode article : msg.getNews()) {
			article.setId(IdWorkerManager.getIdWorker(NewsNode.class).getId());
			msgRepository.saveSendNewsArticles(msg.getCust_id(), msg.getId(), article);
		}
	}
	
	/**
	 * save send text message
	 * @param msg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSendTextMsg(
			com.github.cjm0000000.mmt.core.message.send.passive.TextMessage msg) {
		saveSendMsg(msg);
		msgRepository.saveSendTextMsg(msg);
	}
	
	/**
	 * save common receive event
	 * @param event
	 */
	private void saveRecvEvent(SimpleEvent event){
		prepareMsgId(event);
		eventRepository.saveRecvEventDetail(event);
	}
	
	/**
	 * save common receive message 
	 * @param msg
	 */
	private void saveRecvMsg(SimpleRecvMessage msg){
		prepareMsgId(msg);
		msgRepository.saveRecvMsgDetail(msg);
	}
	
	/**
	 * save common send message 
	 * @param msg
	 */
	private void saveSendMsg(BaseMessage msg){
		prepareMsgId(msg);
		msgRepository.saveSendMsgDetail(msg);
	}
	
	/**
	 * 生成消息ID
	 * @param msg
	 */
	private void prepareMsgId(BaseMessage msg){
		msg.setId(IdWorkerManager.getIdWorker(BaseMessage.class).getId());
	}
}
