package lemon.shared.test.access;

import static org.junit.Assert.*;
import lemon.shared.access.Access;
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
public class AccessRepositoryTest {
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
	
	
	/**
	 * business: access
	 */
	@Test
	public void access(){
		Access acc = new Access();
		acc.setCust_id(CUST_ID);
		acc.setEchostr("5945972242298150991");
		acc.setId(IdWorkerManager.getIdWorker(Access.class).getId());
		acc.setNonce("1384404554");
		acc.setService_type(ServiceType.WEIXIN);
		acc.setSignature("ceced3c856eb62356041dfa5b5d73dc417eba36c");
		acc.setTimestamp_api("1384357959");
		acc.setToken("58fd5e5c1228c6501fcff382b7488ca2");
		int result = accessRepository.saveAccessLog(acc);
		assertNotEquals(0, result);
	}
	
	@Test
	public void accessToken(){
		AccessToken at = new AccessToken();
		at.setAccess_token("eoFYqO38FBRBdSteODittGkw_i7AQD-rPn8vULJscdcuJticQvBUTpS-QXo6IRl2-cJY01YI-jVojpqdSITawfe66qPRJ7JROJwtTOju990xY3SsWg5UNjm2LC_WxtfdfTVYGV0R27Mb9o3gbTEMsg");
		at.setCust_id(CUST_ID);
		at.setExpires_in(7200);
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
	public void accessTokenLog(){
		AccessTokenLog log = new AccessTokenLog();
		log.setAppid("wx18773e3539a14c3b");
		log.setCust_id(CUST_ID);
		log.setGrant_type("client_credential");
		log.setResult("{\"access_token\":\"eoFYqO38FBRBdSteODittGkw_i7AQD-rPn8vULJscdcuJticQvBUTpS-QXo6IRl2-cJY01YI-jVojpqdSITawfe66qPRJ7JROJwtTOju990xY3SsWg5UNjm2LC_WxtfdfTVYGV0R27Mb9o3gbTEMsg\",\"expires_in\":7200}");
		log.setSecret("dd64d7721bd22dfd36d4dcc2c9c6ee68");
		log.setService_type(ServiceType.WEIXIN);
		log.setId(IdWorkerManager.getIdWorker(AccessTokenLog.class).getId());
		accessRepository.saveAccessTokenLog(log);
		assertNotEquals(0, log.getId());
	}
	
}
