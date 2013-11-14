package lemon.shared.test.message.local;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.message.local.LocalMsgBean;
import lemon.shared.message.local.persistence.LocalMsgBeanRepository;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class LocalMsgBeanRepositoryTest {
	private LocalMsgBeanRepository msgBeanMapper;
	private ApplicationContext acx;
	
	private static final int CUST_ID = -5743;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgBeanMapper = acx.getBean(LocalMsgBeanRepository.class);
		assertNotNull(msgBeanMapper);
	}
	
	@Test
	public void addL1Msg(){
		LocalMsgBean mb = addMsg(1);
		//verify
		LocalMsgBean mb2 = msgBeanMapper.getL1Msg(CUST_ID, mb.getKey());
		assertNotNull(mb2);
		assertEquals(mb.getValue(), mb2.getValue());
	}
	
	@Test
	public void addL2Msg(){
		LocalMsgBean mb = addMsg(2);
		//verify
		LocalMsgBean mb2 = msgBeanMapper.getL2Msg(CUST_ID, mb.getKey());
		assertNotNull(mb2);
		assertEquals(mb.getValue(), mb2.getValue());
	}
	
	@Test
	public void addL3Msg(){
		LocalMsgBean mb = addMsg(3);
		//verify
		LocalMsgBean mb2 = msgBeanMapper.getL3Msg(mb.getKey());
		assertNotNull(mb2);
		assertEquals(mb.getValue(), mb2.getValue());
	}
	
	@Test
	public void getL1List(){
		msgBeanMapper.deleteMsgByCustomer(CUST_ID, 1);
		for (int i = 0; i < 5; i++) {
			addMsg(1);
		}
		List<LocalMsgBean> list = msgBeanMapper.getL1List(CUST_ID, 0, 10);
		assertNotNull(list);
		assertEquals(5, list.size());
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
	
	private LocalMsgBean addMsg(int level) {
		LocalMsgBean mb = new LocalMsgBean();
		mb.setCust_id(CUST_ID);
		mb.setKey(UUID.randomUUID().toString());
		mb.setValue(UUID.randomUUID().toString());
		mb.setId(IdWorkerManager.getIdWorker(LocalMsgBean.class).getId());
		assertNotEquals(0, msgBeanMapper.addMsg(mb, level));
		return mb;
	}
}
