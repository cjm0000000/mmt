package lemon.yixin.test.dao;

import java.security.SecureRandom;
import java.util.Date;

import lemon.shared.log.bean.SiteAccess;
import lemon.shared.log.mapper.MMTLogManager;
import lemon.shared.service.ServiceType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YXLogTest {
	private static Log logger = LogFactory.getLog(YXLogTest.class);
	private MMTLogManager mmtLogManager;
	private ApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		mmtLogManager = acx.getBean(MMTLogManager.class);
		assertNotNull(mmtLogManager);
	}

	@Test
	public void siteAccessLog() {
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			SecureRandom rnd = new SecureRandom();
			SiteAccess log = new SiteAccess();
			log.setSignature("signature123213123123123");
			log.setTimestamp_api(new Date().getTime() + "");
			log.setNonce(rnd.nextInt(10000) + "");
			log.setEchostr("echosresdadakdov");
			log.setCust_id(123);
			log.setToken("token");
			log.setService_type(ServiceType.YIXIN);
			mmtLogManager.saveSiteAccessLog(log);
			logger.info("ID=" + log.getId());
			assertNotEquals(0, log.getId());
		}
		logger.info("end...");
	}
	
}
