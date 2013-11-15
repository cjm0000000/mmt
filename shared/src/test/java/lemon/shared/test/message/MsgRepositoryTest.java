package lemon.shared.test.message;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.UUID;

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
import lemon.shared.message.persistence.MsgRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MsgRepositoryTest {
	private ApplicationContext acx;
	private MsgRepository msgRepository;
	private static final int CUST_ID = -5743;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgRepository = acx.getBean(MsgRepository.class);
		assertNotNull(msgRepository);
	}

	@Test
	public void saveRecvYXAudioMsg() {
		YXAudioMessage msg = new YXAudioMessage();
		prepareMsg(msg);
		msg.setName("JUnit Test Audio");
		msg.setUrl("http://www.baidu.com/url");
		assertNotEquals(0, msgRepository.saveRecvYXAudioMsg(msg));
	}

	@Test
	public void saveRecvEventMsg() {
		EventMessage msg = new EventMessage();
		prepareMsg(msg);
		msg.setEventKey(UUID.randomUUID().toString());
		msg.setEventType("CLICK");
		assertNotEquals(0, msgRepository.saveRecvEventMsg(msg));
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
		Message msg = new Message("text");
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
		WXVideoMessage msg = new WXVideoMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setThumbMediaId(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvWXVideoMessage(msg));
	}
	
	@Test
	public void saveRecvYXVideoMessage(){
		YXVideoMessage msg = new YXVideoMessage();
		prepareMsg(msg);
		msg.setUrl("http://www.163.com/" + UUID.randomUUID().toString());
		msg.setName("Name " + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvYXVideoMessage(msg));
	}
	
	@Test
	public void saveRecvWXVoiceMsg(){
		WXVoiceMessage msg = new WXVoiceMessage();
		prepareMsg(msg);
		msg.setMediaId(UUID.randomUUID().toString());
		msg.setFormat("amr");
		msg.setRecognition("Rec " + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvWXVoiceMsg(msg));
	}
	
	@Test
	public void saveRecvYXMusicMsg(){
		YXMusicMessage msg = new YXMusicMessage();
		prepareMsg(msg);
		msg.setUrl("http://music.yixin.im/" + UUID.randomUUID().toString());
		msg.setName("Music Name " + UUID.randomUUID().toString());
		msg.setDesc(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveRecvYXMusicMsg(msg));
	}
	
	@Test
	public void saveSendMsgDetail(){
		Message msg = new Message("music");
		prepareMsg(msg);
		assertNotEquals(0, msgRepository.saveSendMsgDetail(msg));
	}
	
	@Test
	public void saveSendNewsArticles(){
		Article art = new Article();
		art.setDescription("description" + UUID.randomUUID().toString());
		art.setId(IdWorkerManager.getIdWorker(Article.class).getId());
		art.setPicUrl("http://pic.yixin.com/" + UUID.randomUUID().toString());
		art.setTitle("Title " + UUID.randomUUID().toString());
		art.setUrl("http://www.google.com/" + UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveSendNewsArticles(CUST_ID,IdWorkerManager.getIdWorker(Message.class).getId(),art));
	}
	
	@Test
	public void saveSendNewsMsg(){
		NewsMessage msg = new NewsMessage();
		prepareMsg(msg);
		msg.setArticleCount(new SecureRandom().nextInt(10));
		assertNotEquals(0, msgRepository.saveSendNewsMsg(msg));
	}
	
	@Test
	public void saveSendTextMsg(){
		TextMessage msg = new TextMessage();
		prepareMsg(msg);
		msg.setContent(UUID.randomUUID().toString());
		assertNotEquals(0, msgRepository.saveSendTextMsg(msg));
	}

	private void prepareMsg(Message msg) {
		msg.setCreateTime(System.currentTimeMillis() / 1000);
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("fromUserName");
		msg.setId(IdWorkerManager.getIdWorker(Message.class).getId());
		msg.setMsgId(System.currentTimeMillis());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("toUserName");
	}
	
}
