package lemon.shared.test.log;

import static org.junit.Assert.*;
import lemon.shared.entity.Action;
import lemon.shared.entity.ServiceType;
import lemon.shared.log.bean.AccessTokenLog;
import lemon.shared.log.bean.CustomMenuLog;
import lemon.shared.log.mapper.MMTLogManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MMTLogTest {
	private ApplicationContext acx;
	private MMTLogManager mmtLogManager;
	
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		mmtLogManager = acx.getBean(MMTLogManager.class);
		assertNotNull(mmtLogManager);
	}
	
	@Test
	public void saveAccessTokenLog(){
		AccessTokenLog log = new AccessTokenLog();
		log.setAppid("APPID");
		log.setCust_id(1);
		log.setGrant_type("grant_type");
		log.setResult("result");
		log.setSecret("secret");
		log.setService_type(ServiceType.YIXIN);
		log.setTimestamp(null);
		mmtLogManager.saveAccessTokenLog(log);
		assertNotEquals(0, log.getId());
	}
	
	@Test
	public void saveCustomMenuLog(){
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token("accesstoken");
		log.setAction(Action.DELETE);
		log.setCust_id(1);
		log.setMsg("msg");
		log.setResult("result");
		log.setService_type(ServiceType.WEIXIN);
		mmtLogManager.saveCustomMenuLog(log);
		assertNotEquals(0, log.getId());
	}
}
