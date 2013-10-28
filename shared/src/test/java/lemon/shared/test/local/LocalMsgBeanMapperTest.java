package lemon.shared.test.local;

import static org.junit.Assert.*;

import lemon.shared.message.local.LocalMsgBean;
import lemon.shared.message.local.persistence.LocalMsgBeanRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class LocalMsgBeanMapperTest {
	private LocalMsgBeanRepository msgBeanMapper;
	private ApplicationContext acx;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgBeanMapper = acx.getBean(LocalMsgBeanRepository.class);
		assertNotNull(msgBeanMapper);
	}
	
	@Test
	@Ignore
	public void getL1Msg(){
		LocalMsgBean mb = msgBeanMapper.getL1Msg(1, "q1");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void getL2Msg(){
		LocalMsgBean mb = msgBeanMapper.getL2Msg(1, "key");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void getL3Msg(){
		LocalMsgBean mb = msgBeanMapper.getL3Msg("%a%");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void addMsg(){
		LocalMsgBean mb = new LocalMsgBean();
		mb.setCust_id(1);
		mb.setKey("key");
		mb.setValue("vava");
		msgBeanMapper.addMsg(mb, 2);
	}
	
	@Test
	@Ignore
	public void update(){
		LocalMsgBean mb1 = new LocalMsgBean();
		mb1.setCust_id(1);
		mb1.setKey("key1");
		mb1.setValue("vava1");
		msgBeanMapper.addMsg(mb1, 1);
		
		mb1.setCust_id(2);
		mb1.setKey("keyyyy");
		mb1.setValue("VVVV");
		msgBeanMapper.updateMsg(mb1, 1);
		
		LocalMsgBean mb = msgBeanMapper.getL1Msg(1, "keyyyy");
		assertEquals(mb.getValue(), mb1.getValue());
	}
	
	@Test
	@Ignore
	public void delete(){
		LocalMsgBean mb1 = new LocalMsgBean();
		mb1.setCust_id(1);
		mb1.setKey("key1");
		mb1.setValue("vava1");
		msgBeanMapper.addMsg(mb1, 3);
		
		LocalMsgBean mb2 = new LocalMsgBean();
		mb2.setCust_id(1);
		mb2.setKey("key12");
		mb2.setValue("vava1");
		msgBeanMapper.addMsg(mb2, 3);
		msgBeanMapper.deleteMsg(mb1.getId()+","+mb2.getId(), 3);
	}
	
	@Test
	@Ignore
	public void count(){
		int l2_cnt = msgBeanMapper.getL2Count(1);
		int l3_cnt = msgBeanMapper.getL3Count();
		System.out.println(l2_cnt);
		System.out.println(l3_cnt);
	}
	
	@Test
	public void get(){
		msgBeanMapper.getMsg(1, 3);
	}
}
