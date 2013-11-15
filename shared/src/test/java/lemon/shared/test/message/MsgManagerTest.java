package lemon.shared.test.message;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.Article;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.service.ServiceType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MsgManagerTest {
	private ApplicationContext acx;
	private MsgManager msgManager;
	private static final int CUST_ID = -5743;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgManager = acx.getBean(MsgManager.class);
		assertNotNull(msgManager);
	}

	@Test
	public void saveRecvAudioMsg() {
		YXAudioMessage msg = new YXAudioMessage();
		prepareMsg(msg);
		msg.setName("JUnit Test Audio");
		msg.setUrl("http://www.baidu.com/url");
		msgManager.saveRecvAudioMsg(msg);
		YXAudioMessage msg2 = msgManager.getRecvYXAudioMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getName(), msg2.getName());
		assertEquals(msg.getUrl(), msg2.getUrl());
	}

	@Test
	public void saveRecvEventMsg() {
		EventMessage msg = new EventMessage();
		prepareMsg(msg);
		msg.setEventKey(UUID.randomUUID().toString());
		msg.setEventType("CLICK");
		msgManager.saveRecvEventMsg(msg);
		EventMessage msg2 = msgManager.getRecvEventMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getEventKey(), msg2.getEventKey());
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
		WXVideoMessage msg = new WXVideoMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setThumbMediaId(UUID.randomUUID().toString());
		msgManager.saveRecvVideoMsg(msg);
		WXVideoMessage msg2 = msgManager.getRecvWXVideoMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getMediaId(), msg2.getMediaId());
		assertEquals(msg.getThumbMediaId(), msg2.getThumbMediaId());
	}
	
	@Test
	public void saveRecvYXVideoMessage(){
		YXVideoMessage msg = new YXVideoMessage();
		prepareMsg(msg);
		msg.setUrl("http://www.163.com/" + UUID.randomUUID().toString());
		msg.setName("Name " + UUID.randomUUID().toString());
		msgManager.saveRecvVideoMsg(msg);
		YXVideoMessage msg2 = msgManager.getRecvYXVideoMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getUrl(), msg2.getUrl());
		assertEquals(msg.getName(), msg2.getName());
	}
	
	@Test
	public void saveRecvWXVoiceMsg(){
		WXVoiceMessage msg = new WXVoiceMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setFormat("amr");
		msg.setRecognition("Rec " + UUID.randomUUID().toString());
		msgManager.saveRecvVoiceMsg(msg);
		WXVoiceMessage msg2 = msgManager.getRecvWXVoiceMsg(msg.getId());
		assertRecvMsg(msg, msg2);
		assertEquals(msg.getMediaId(), msg2.getMediaId());
		assertEquals(msg.getFormat(), msg2.getFormat());
		assertEquals(msg.getRecognition(), msg2.getRecognition());
	}
	
	@Test
	public void saveRecvYXMusicMsg(){
		YXMusicMessage msg = new YXMusicMessage();
		prepareMsg(msg);
		msg.setUrl("http://music.yixin.im/" + UUID.randomUUID().toString());
		msg.setName("Music Name " + UUID.randomUUID().toString());
		msg.setDesc(UUID.randomUUID().toString());
		msgManager.saveRecvMusicMsg(msg);
		YXMusicMessage msg2 = msgManager.getRecvYXMusicMsg(msg.getId());
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
		Article[] arts = new Article[count];
		for (int i = 0; i < count; i++) {
			Article art = new Article();
			art.setDescription("description"+i);
			art.setPicUrl("http://pic.google.com/" + UUID.randomUUID().toString());
			art.setTitle("Title " + i);
			art.setUrl("http://www.google.com/" + UUID.randomUUID().toString());
			arts[i] = art;
		}
		msg.setArticles(arts);
		msg.setArticleCount(count);
		msgManager.saveSendNewsMsg(msg);
		NewsMessage msg2 = msgManager.getSendNewsMsg(msg.getId());
		assertMsg(msg, msg2);
		assertEquals(msg.getArticleCount(), msg2.getArticleCount());
		List<Article> arts2 = msgManager.getArticlesByNews(msg.getId());
		assertNotNull(arts2);
		assertTrue(arts2.containsAll(arts2));
	}
	
	@Test
	public void saveSendTextMsg(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		msgManager.saveSendTextMsg(msg);
		TextMessage msg2 = msgManager.getSendTextMsg(msg.getId());
		assertMsg(msg, msg2);
		assertEquals(msg.getContent(), msg2.getContent());
	}

	private void prepareMsg(Message msg) {
		msg.setCreateTime(System.currentTimeMillis() / 1000);
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("fromUserName");
		msg.setMsgId(System.currentTimeMillis());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("toUserName");
	}
	
	/**
	 * 基础Message断言
	 * @param actual
	 * @param expected
	 */
	private void assertMsg(Message actual, Message expected){
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
	
	private void assertRecvMsg(Message actual, Message expected){
		assertMsg(actual, expected);
		assertEquals(expected.getMsgId(), actual.getMsgId());
	}
	
}
