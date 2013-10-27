package lemon.shared.test.message.log;

import static org.junit.Assert.*;
import lemon.shared.message.log.MsgLog;
import lemon.shared.message.log.MsgLogManager;
import lemon.shared.service.ServiceType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MsgLogManagerTest {
	private ApplicationContext acx;
	private MsgLogManager msgLogManager;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgLogManager = acx.getBean(MsgLogManager.class);
		assertNotNull(msgLogManager);
	}
	
	@Test
	public void saveRecvLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveRecvLog(log));
	}

	@Test
	public void saveSendLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveSendLog(log));
	}
	
	private MsgLog generateMsgLog(){
		MsgLog log = new MsgLog();
		log.setCust_id(10);
		log.setMsg("hello msg");
		log.setService_type(ServiceType.OTHER);
		log.setTimestamp(null);
		return log;
	}
}
