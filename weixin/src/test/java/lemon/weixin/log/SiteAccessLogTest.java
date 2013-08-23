package lemon.weixin.log;

import java.security.SecureRandom;
import java.util.Date;

import lemon.shared.weixin.bean.SiteAccessLog;
import lemon.shared.weixin.dao.WXLogManager;

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
public class SiteAccessLogTest {
	private static Log logger = LogFactory.getLog(SiteAccessLogTest.class);
	private WXLogManager wXLogManager;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		wXLogManager = (WXLogManager) acx.getBean(WXLogManager.class);
		assertNotNull(wXLogManager);
	}

	@Test
	public void saveLog() {
		logger.info("begin...");
		SecureRandom rnd = new SecureRandom();
		SiteAccessLog log = new SiteAccessLog();
		log.setSignature("signature123213123123123");
		log.setTimestamp(new Date().getTime() + "");
		log.setNonce(rnd.nextInt(10000) + "");
		log.setEchostr("echosresdadakdov");
		wXLogManager.saveSiteAccessLog(log);
		logger.info("ID=" + log.getId());
		assertNotEquals(0, log.getId());
	}
}
