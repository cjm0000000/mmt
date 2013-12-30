package com.github.cjm0000000.shared.test.message;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

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
import com.github.cjm0000000.mmt.shared.message.MsgManager;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test cases for 
 * @author lemon
 * @version 1.0
 *
 */
public class MsgManager_Test extends AbstractTester {
	@Autowired
	private MsgManager msgManager;
	
	@Override
	public void defaultCase() {
		saveSendTextMsg();
	}

	@Test
	public void saveRecvAudioMsg() {
		AudioMessage msg = new AudioMessage();
		prepareMsg(msg);
		msg.setName("JUnit Test Audio");
		msg.setUrl("http://www.baidu.com/url");
		msgManager.saveRecvAudioMsg(msg);
		AudioMessage msg2 = msgManager.getRecvYXAudioMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getName(), msg2.getName());
		assertEquals(msg.getUrl(), msg2.getUrl());
	}

	@Test
	public void saveRecvSimpleEvent() {
		SimpleEvent msg = new SimpleEvent();
		prepareMsg(msg);
		//msg.setEventKey(UUID.randomUUID().toString());
		msg.setEventType(EventType.subscribe);
		msgManager.saveRecvEventMsg(msg);
		SimpleEvent msg2 = msgManager.getRecvEventMsg(msg.getId());
		//assertRecvMsg(msg, msg2);
		assertEquals(msg.getEventType(), msg2.getEventType());
	}

	@Test
	public void saveRecvImageMsg() {
		ImageMessage msg = new ImageMessage();
		prepareMsg(msg);
		msg.setPicUrl("http://www.pic.url");
		msg.setMediaId("ZTjFiu7uLSfqupgRn2z4uZT8JqulZXKntm6ERVXrFtcppQOTF9x8Ow-cCb1yoUoy");
		msgManager.saveRecvImageMsg(msg);
		ImageMessage msg2 = msgManager.getRecvImageMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getPicUrl(), msg2.getPicUrl());
		assertEquals(msg.getMediaId(), msg2.getMediaId());
	}

	@Test
	public void saveRecvLinkMsg() {
		LinkMessage msg = new LinkMessage();
		prepareMsg(msg);
		msg.setTitle("LINK TITLE " + UUID.randomUUID().toString());
		msg.setDescription("description " + UUID.randomUUID().toString());
		msg.setUrl("http://www.mmt.com/" + UUID.randomUUID().toString());
		msgManager.saveRecvLinkMsg(msg);
		LinkMessage msg2 = msgManager.getRecvLinkMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getTitle(), msg2.getTitle());
		assertEquals(msg.getUrl(), msg2.getUrl());
		assertEquals(msg.getDescription(), msg2.getDescription());
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
		msgManager.saveRecvLocationMsg(msg);
		LocationMessage msg2 = msgManager.getRecvLocationMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getLocation_X(), msg2.getLocation_X(),0.000001D);
		assertEquals(msg.getLocation_Y(), msg2.getLocation_Y(),0.000001D);
		assertEquals(msg.getScale(), msg2.getScale());
		assertEquals(msg.getLabel(), msg2.getLabel());
	}
	
	@Test
	public void saveRecvTextMsg(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		msgManager.saveRecvTextMsg(msg);
		TextMessage msg2 = msgManager.getRecvTextMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getContent(), msg2.getContent());
	}
	
	@Test
	public void saveRecvWXVideoMessage(){
		com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage msg = new com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setThumbMediaId(UUID.randomUUID().toString());
		msgManager.saveRecvVideoMsg(msg);
		com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage msg2 = msgManager.getRecvWXVideoMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getMediaId(), msg2.getMediaId());
		assertEquals(msg.getThumbMediaId(), msg2.getThumbMediaId());
	}
	
	@Test
	public void saveRecvYXVideoMessage(){
		com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage msg = new com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage();
		prepareMsg(msg);
		msg.setUrl("http://www.163.com/" + UUID.randomUUID().toString());
		msg.setName("Name " + UUID.randomUUID().toString());
		msgManager.saveRecvVideoMsg(msg);
		com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage msg2 = msgManager.getRecvYXVideoMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getUrl(), msg2.getUrl());
		assertEquals(msg.getName(), msg2.getName());
	}
	
	@Test
	public void saveRecvWXVoiceMsg(){
		VoiceMessage msg = new VoiceMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setFormat("amr");
		msgManager.saveRecvVoiceMsg(msg);
		VoiceMessage msg2 = msgManager.getRecvWXVoiceMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getMediaId(), msg2.getMediaId());
		assertEquals(msg.getFormat(), msg2.getFormat());
	}
	
	@Test
	public void saveRecvYXMusicMsg(){
		MusicMessage msg = new MusicMessage();
		prepareMsg(msg);
		msg.setUrl("http://music.yixin.im/" + UUID.randomUUID().toString());
		msg.setName("Music Name " + UUID.randomUUID().toString());
		msg.setDesc(UUID.randomUUID().toString());
		msgManager.saveRecvMusicMsg(msg);
		MusicMessage msg2 = msgManager.getRecvYXMusicMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getMimeType(), msg2.getMimeType());
		assertEquals(msg.getUrl(), msg2.getUrl());
		assertEquals(msg.getName(), msg2.getName());
		assertEquals(msg.getDesc(), msg2.getDesc());
	}
	
	
	@Test
	public void saveSendNewsMsg(){
		NewsMessage msg = new NewsMessage();
		prepareMsg(msg);
		int count = new SecureRandom().nextInt(10);
		while(count <= 0)
			count = new SecureRandom().nextInt(10);
		NewsNode[] arts = new NewsNode[count];
		for (int i = 0; i < count; i++) {
			NewsNode art = new NewsNode();
			art.setDescription("description"+i);
			art.setPicUrl("http://pic.google.com/" + UUID.randomUUID().toString());
			art.setTitle("Title " + i);
			art.setUrl("http://www.google.com/" + UUID.randomUUID().toString());
			arts[i] = art;
		}
		msg.setNews(arts);
		msgManager.saveSendNewsMsg(msg);
		NewsMessage msg2 = msgManager.getSendNewsMsg(msg.getId());
		assertMsg(msg, msg2);
		assertEquals(msg.getArticleCount(), msg2.getArticleCount());
		List<NewsNode> arts2 = msgManager.getArticlesByNews(msg.getId());
		assertNotNull(arts2);
		assertTrue(arts2.containsAll(arts2));
	}
	
	private void saveSendTextMsg(){
		com.github.cjm0000000.mmt.core.message.send.passive.TextMessage msg = new com.github.cjm0000000.mmt.core.message.send.passive.TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		msgManager.saveSendTextMsg(msg);
		TextMessage msg2 = msgManager.getSendTextMsg(msg.getId());
		assertMsg(msg, msg2);
		assertEquals(msg.getContent(), msg2.getContent());
	}

	private void prepareMsg(BaseMessage msg) {
		msg.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("fromUserName");
		//msg.setMsgId(System.currentTimeMillis());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("toUserName");
	}
	
	/**
	 * 基础Message断言
	 * @param actual
	 * @param expected
	 */
	private void assertMsg(BaseMessage actual, BaseMessage expected){
		assertNotNull(actual);
		assertNotNull(expected);
		assertEquals(expected.getCreateTime(), actual.getCreateTime());
		assertEquals(expected.getCust_id(), actual.getCust_id());
		assertEquals(expected.getFromUserName(), actual.getFromUserName());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getService_type(), actual.getService_type());
		assertEquals(expected.getTimestamp(), actual.getTimestamp());
		assertEquals(expected.getToUserName(), actual.getToUserName());
	}
	
	private void assertRecvMsg(SimpleRecvMessage actual, SimpleRecvMessage expected){
		assertMsg(actual, expected);
		assertEquals(expected.getMsgId(), actual.getMsgId());
	}

}
