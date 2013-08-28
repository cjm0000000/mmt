package lemon.weixin.log;

import java.security.SecureRandom;
import java.util.Date;

import lemon.weixin.bean.log.MsgLog;
import lemon.weixin.bean.log.SiteAccessLog;
import lemon.weixin.dao.WXLogManager;

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
public class WXLogTest {
	private static Log logger = LogFactory.getLog(WXLogTest.class);
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
		for (int i = 0; i < 10; i++) {
			SecureRandom rnd = new SecureRandom();
			SiteAccessLog log = new SiteAccessLog();
			log.setSignature("signature123213123123123");
			log.setTimestamp(new Date().getTime() + "");
			log.setNonce(rnd.nextInt(10000) + "");
			log.setEchostr("echosresdadakdov");
			log.setCust_id(123);
			log.setToken("token");
			wXLogManager.saveSiteAccessLog(log);
			logger.info("ID=" + log.getId());
			assertNotEquals(0, log.getId());
			wXLogManager.deleteSiteAccessLog(log.getId());
		}
		logger.info("end...");
	}
	@Test
	public void msgLog(){
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			MsgLog receive = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377529956729</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[hello,weixin, I am \"lemon\".]]></Content></xml>");
			wXLogManager.saveMessageLog(receive);
			logger.info("ID=" + receive.getId());
			assertNotEquals(0, receive.getId());
			wXLogManager.deleteMsgLog(receive.getId());
			
			MsgLog send = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377530060575</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>2</ArticleCount><Articles><item><Title><![CDATA[Title A1]]></Title><Description><![CDATA[DESC A1]]></Description><PicUrl><![CDATA[pic.taobao.com/aaas/asdf.jpg]]></PicUrl><Url><![CDATA[http://www.baidu.com]]></Url></item><item><Title><![CDATA[Title A2]]></Title><Description><![CDATA[DESC A2]]></Description><PicUrl><![CDATA[pic2.taobao.com/aaas/asdf222.jpg]]></PicUrl><Url><![CDATA[http://www.yousas.com]]></Url></item></Articles></xml>");
			wXLogManager.saveMessageLog(send);
			logger.info("ID=" + send.getId());
			assertNotEquals(0, send.getId());
			wXLogManager.deleteMsgLog(send.getId());
		}
		logger.info("end...");
	}
}
