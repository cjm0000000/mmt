package com.github.cjm0000000.shared.test.message;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
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
import com.github.cjm0000000.mmt.shared.message.persistence.MsgRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;
import com.github.cjm0000000.shared.test.AbstractTester;

public class MsgRepository_Test extends AbstractTester {
	@Autowired
	private MsgRepository msgRepository;

	@Test
	@Ignore /** Because H2 does't support MYSQL function: FROM_UNIXTIME */
	public void getRecvMsgList(){
		List<BaseMessage> list = msgRepository.getRecvMsgList(CUST_ID, null, null, 0, 10);
		assertNotNull(list);
		list = msgRepository.getRecvMsgList(CUST_ID, ServiceType.WEIXIN, null, 0, 10);
		assertNotNull(list);
		list = msgRepository.getRecvMsgList(CUST_ID, ServiceType.WEIXIN, "image", 0, 10);
		assertNotNull(list);
		list = msgRepository.getRecvMsgList(CUST_ID, null, "image", 0, 10);
		assertNotNull(list);
	}
	
	@Test
	public void getRecvTextMsgList(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvTextMsg(msg));
		long[] ids = {msg.getId()};
		List<TextMessage> list = msgRepository.getRecvTextMsgList(ids);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(list.get(0).getContent(), msg.getContent());
	}
	
	@Test
	public void getRecvMsgDetail(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvMsgDetail(msg));
		long msgId = msg.getId();
		BaseMessage msgDetail = msgRepository.getRecvMsgDetail(msgId);
		assertNotNull(msgDetail);
		assertEquals(msgDetail.getMsgType(), msg.getMsgType());
	}

	@Test
	public void saveRecvYXAudioMsg() {
		AudioMessage msg = new AudioMessage();
		prepareMsg(msg);
		msg.setName("JUnit Test Audio");
		msg.setUrl("http://www.baidu.com/url");
		assertNotEquals(0, msgRepository.saveRecvAudioMsg(msg));
	}

	@Test
	public void saveRecvEventMsg() {
		SimpleEvent msg = new SimpleEvent();
		prepareMsg(msg);
		//msg.setEventKey(UUID.randomUUID().toString());
		msg.setEventType(EventType.subscribe);
		//TODO 修改event repository 体系
		assertNotEquals(0, msgRepository.saveRecvEventMsg(null));
	}

	@Test
	public void saveRecvImageMsg() {
		ImageMessage msg = new ImageMessage();
		prepareMsg(msg);
		msg.setPicUrl("http://www.pic.url");
		msg.setMediaId("ZTjFiu7uLSfqupgRn2z4uZT8JqulZXKntm6ERVXrFtcppQOTF9x8Ow-cCb1yoUoy");
		assertNotEquals(0, msgRepository.saveRecvImageMsg(msg));
	}

	@Test
	public void saveRecvLinkMsg() {
		LinkMessage msg = new LinkMessage();
		prepareMsg(msg);
		msg.setTitle("LINK TITLE " + UUID.randomUUID().toString());
		msg.setDescription("description " + UUID.randomUUID().toString());
		msg.setUrl("http://www.mmt.com/" + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvLinkMsg(msg));
	}

	@Test
	public void saveRecvLocationMsg() {
		LocationMessage msg = new LocationMessage();
		prepareMsg(msg);
		DecimalFormat df = new DecimalFormat("########.000000");
		double x = Double.valueOf(df.format(new SecureRandom().nextDouble()));
		double y = Double.valueOf(df.format(new SecureRandom().nextDouble()));
		msg.setLocation_X(x);
		msg.setLocation_Y(y);
		msg.setScale(new SecureRandom().nextInt(50));
		msg.setLabel(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvLocationMsg(msg));
	}
	
	@Test
	public void saveRecvMsgDetail(){
		SimpleRecvMessage msg = new SimpleRecvMessage("text");
		prepareMsg(msg);
		assertNotEquals(0, msgRepository.saveRecvMsgDetail(msg));
	}
	
	@Test
	public void saveRecvTextMsg(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvTextMsg(msg));
	}
	
	@Test
	public void saveRecvWXVideoMessage(){
		com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage msg = new com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setThumbMediaId(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvWXVideoMessage(msg));
	}
	
	@Test
	public void saveRecvYXVideoMessage(){
		com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage msg = new com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage();
		prepareMsg(msg);
		msg.setUrl("http://www.163.com/" + UUID.randomUUID().toString());
		msg.setName("Name " + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvYXVideoMessage(msg));
	}
	
	@Test
	public void saveRecvWXVoiceMsg(){
		VoiceMessage msg = new VoiceMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setFormat("amr");
		assertNotEquals(0, msgRepository.saveRecvVoiceMsg(msg));
	}
	
	@Test
	public void saveRecvYXMusicMsg(){
		MusicMessage msg = new MusicMessage();
		prepareMsg(msg);
		msg.setUrl("http://music.yixin.im/" + UUID.randomUUID().toString());
		msg.setName("Music Name " + UUID.randomUUID().toString());
		msg.setDesc(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvYXMusicMsg(msg));
	}
	
	@Test
	public void saveSendMsgDetail(){
		BaseMessage msg = new BaseMessage("music");
		prepareMsg(msg);
		assertNotEquals(0, msgRepository.saveSendMsgDetail(msg));
	}
	
	@Test
	public void saveSendNewsArticles(){
		NewsNode art = new NewsNode();
		art.setDescription("description" + UUID.randomUUID().toString());
		art.setId(IdWorkerManager.getIdWorker(NewsNode.class).getId());
		art.setPicUrl("http://pic.yixin.com/" + UUID.randomUUID().toString());
		art.setTitle("Title " + UUID.randomUUID().toString());
		art.setUrl("http://www.google.com/" + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveSendNewsArticles(CUST_ID,IdWorkerManager.getIdWorker(NewsNode.class).getId(),art));
	}
	
	@Test
	public void saveSendNewsMsg(){
		NewsMessage msg = new NewsMessage();
		prepareMsg(msg);
		assertNotEquals(0, msgRepository.saveSendNewsMsg(msg));
	}
	
	@Test
	public void saveSendTextMsg(){
		com.github.cjm0000000.mmt.core.message.send.passive.TextMessage msg = new com.github.cjm0000000.mmt.core.message.send.passive.TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveSendTextMsg(msg));
	}

	private void prepareMsg(BaseMessage msg) {
		msg.setCreateTime(String.valueOf((System.currentTimeMillis() / 1000)));
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("fromUserName");
		msg.setId(IdWorkerManager.getIdWorker(BaseMessage.class).getId());
		// TODO msg.setMsgId(System.currentTimeMillis());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("toUserName");
	}
	
}
