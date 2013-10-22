package lemon.yixin.test.dao;

import java.security.SecureRandom;
import java.util.Date;

import lemon.shared.log.bean.SiteAccess;
import lemon.yixin.log.bean.MsgLog;
import lemon.yixin.log.bean.SubscribeLog;
import lemon.yixin.log.bean.UnSubscribeLog;
import lemon.yixin.log.mapper.YXLogManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YXLogTest {
	private static Log logger = LogFactory.getLog(YXLogTest.class);
	private YXLogManager yxLogManager;
	private AbstractApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		yxLogManager = (YXLogManager) acx.getBean(YXLogManager.class);
		assertNotNull(yxLogManager);
	}
	@After
	public void destory(){
		acx.close();
	}

	@Test
	public void siteAccessLog() {
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			SecureRandom rnd = new SecureRandom();
			SiteAccess log = new SiteAccess();
			log.setSignature("signature123213123123123");
			log.setTimestamp(new Date().getTime() + "");
			log.setNonce(rnd.nextInt(10000) + "");
			log.setEchostr("echosresdadakdov");
			log.setCust_id(123);
			log.setToken("token");
			yxLogManager.saveSiteAccessLog(log);
			logger.info("ID=" + log.getId());
			assertNotEquals(0, log.getId());
			yxLogManager.deleteSiteAccessLog(log.getId());
		}
		logger.info("end...");
	}
	@Test
	public void msgLog(){
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			MsgLog receive = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>");
			yxLogManager.saveMessageLog(receive);
			logger.info("ID=" + receive.getId());
			assertNotEquals(0, receive.getId());
			yxLogManager.deleteMsgLog(receive.getId());
			
			MsgLog send = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>");
			yxLogManager.saveMessageLog(send);
			logger.info("ID=" + send.getId());
			assertNotEquals(0, send.getId());
			yxLogManager.deleteMsgLog(send.getId());
		}
		logger.info("end...");
	}
	
	@Test
	public void subscribeLog(){
		SubscribeLog log = new SubscribeLog();
		log.setCust_id(100);
		log.setYxid("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		yxLogManager.saveSubscribeLog(log);
	}
	
	@Test
	public void unSubscribeLog(){
		UnSubscribeLog log = new UnSubscribeLog();
		log.setCust_id(100);
		log.setYxid("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		yxLogManager.saveUnSubscribeLog(log);
	}
}
