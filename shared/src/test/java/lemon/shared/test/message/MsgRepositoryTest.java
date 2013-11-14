package lemon.shared.test.message;

import static org.junit.Assert.*;

import java.util.UUID;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
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
	public void saveRecvYXAudioMsg(){
		YXAudioMessage msg = new YXAudioMessage();
		prepareMsg(msg);
		msg.setName("JUnit Test Audio");
		msg.setUrl("http://www.baidu.com/url");
		assertNotEquals(0, msgRepository.saveRecvYXAudioMsg(msg));
	}
	
	@Test
	public void saveRecvEventMsg(){
		EventMessage msg = new EventMessage();
		prepareMsg(msg);
		msg.setEventKey(UUID.randomUUID().toString());
		msg.setEventType("CLICK");
		assertNotEquals(0, msgRepository.saveRecvEventMsg(msg));
	}
	
	private void prepareMsg(Message msg){
		msg.setCreateTime(System.currentTimeMillis()/1000);
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("fromUserName");
		msg.setId(IdWorkerManager.getIdWorker(Message.class).getId());
		msg.setMsgId(System.currentTimeMillis());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("toUserName");
	}

}
