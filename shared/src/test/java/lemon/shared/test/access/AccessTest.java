package lemon.shared.test.access;

import static org.junit.Assert.*;

import lemon.shared.access.AccessToken;
import lemon.shared.access.AccessTokenLog;
import lemon.shared.access.persistence.AccessRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class AccessTest {
	private ApplicationContext acx;
	private AccessRepository accessRepository;
	private static final int CUST_ID = -5743;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		accessRepository = acx.getBean(AccessRepository.class);
		assertNotNull(accessRepository);
	}
	
	@Test
	public void accessTokenTest(){
		AccessToken at = new AccessToken();
		at.setAccess_token("access_token");
		at.setCust_id(CUST_ID);
		at.setExpires_in(3600);
		at.setExpire_time((int)(System.currentTimeMillis()/1000) + at.getExpires_in() - 10);
		at.setService_type(ServiceType.WEIXIN);
		at.setId(IdWorkerManager.getIdWorker(AccessToken.class).getId());
		int result = accessRepository.saveAccessToken(at);
		assertNotEquals(0, result);
		at = accessRepository.getAccessToken(CUST_ID, ServiceType.WEIXIN);
		assertNotNull(at);
		accessRepository.deleteAccessToken(CUST_ID, ServiceType.WEIXIN);
		at = accessRepository.getAccessToken(CUST_ID, ServiceType.WEIXIN);
		assertNull(at);
	}
	
	@Test
	public void saveAccessTokenLog(){
		AccessTokenLog log = new AccessTokenLog();
		log.setAppid("APPID");
		log.setCust_id(CUST_ID);
		log.setGrant_type("grant_type");
		log.setResult("result");
		log.setSecret("secret");
		log.setService_type(ServiceType.YIXIN);
		log.setTimestamp(null);
		log.setId(IdWorkerManager.getIdWorker(AccessTokenLog.class).getId());
		accessRepository.saveAccessTokenLog(log);
		assertNotEquals(0, log.getId());
	}
	
}
