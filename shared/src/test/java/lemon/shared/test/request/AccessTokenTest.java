package lemon.shared.test.request;

import static org.junit.Assert.*;
import lemon.shared.request.bean.AccessToken;
import lemon.shared.request.mapper.AccessTokenMapper;
import lemon.shared.service.ServiceType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class AccessTokenTest {
	private ApplicationContext acx;
	private AccessTokenMapper accessTokenMapper;
	private static final int CUST_ID = -5743;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		accessTokenMapper = acx.getBean(AccessTokenMapper.class);
		assertNotNull(accessTokenMapper);
	}
	
	@Test
	public void test(){
		AccessToken at = new AccessToken();
		at.setAccess_token("access_token");
		at.setCust_id(CUST_ID);
		at.setExpires_in(3600);
		at.setExpire_time((int)(System.currentTimeMillis()/1000) + at.getExpires_in() - 10);
		at.setService_type(ServiceType.WEIXIN);
		int result = accessTokenMapper.save(at);
		assertNotEquals(0, result);
		at = accessTokenMapper.get(CUST_ID, ServiceType.WEIXIN);
		assertNotNull(at);
		accessTokenMapper.delete(CUST_ID, ServiceType.WEIXIN);
		at = accessTokenMapper.get(CUST_ID, ServiceType.WEIXIN);
		assertNull(at);
	}
	
	
}
