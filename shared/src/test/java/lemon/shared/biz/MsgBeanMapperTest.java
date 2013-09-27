package lemon.shared.biz;

import static org.junit.Assert.assertNotNull;

import lemon.shared.message.bean.MsgBean;
import lemon.shared.message.mapper.MsgBeanMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MsgBeanMapperTest {
	private MsgBeanMapper msgBeanMapper;
	private AbstractApplicationContext acx;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgBeanMapper = acx.getBean(MsgBeanMapper.class);
		assertNotNull(msgBeanMapper);
	}
	
	@After
	public void destory(){
		acx.close();
	}
	
	@Test
	@Ignore
	public void getL1Msg(){
		MsgBean mb = msgBeanMapper.getL1Msg(1, "1");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void getL2Msg(){
		MsgBean mb = msgBeanMapper.getL2Msg(1, "key");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void getL3Msg(){
		MsgBean mb = msgBeanMapper.getL3Msg("%a%");
		assertNotNull(mb);
	}
	
	@Test
	@Ignore
	public void addMsg(){
		MsgBean mb = new MsgBean();
		mb.setCust_id(1);
		mb.setKey("key");
		mb.setValue("vava");
		msgBeanMapper.addMsg(mb, "2");
	}
}
