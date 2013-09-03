package lemon.weixin.biz;

import java.util.HashMap;
import java.util.Map;

import lemon.shared.api.MmtAPI;
import lemon.shared.util.SecureUtil;
import lemon.weixin.bean.log.SiteAccessLog;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinAPITest {
	private MmtAPI api;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		api = (MmtAPI) acx.getBean(MmtAPI.class);
		assertNotNull(api);
	}
	@Test
	public void testSignature() {
		Map<String, Object> paramMap = new HashMap<>();

		SiteAccessLog log = new SiteAccessLog();
		log.setEchostr("5921940693384818207");
		log.setNonce("1378809259");
		log.setSignature("d864452f69eccd3bc25c4ab94a1723cbba282ff6");
		log.setTimestamp("1377955356");
		log.setCust_id(1);
		log.setToken("QEQdsqo");
		paramMap.put("SiteAccess", log);

		boolean result = api.verifySignature(paramMap);
		assertTrue(result);
	}
	
	@Test
	public void testHSA1(){
		String str = "b";
		String result = "e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98";
		String afterHSA1 = SecureUtil.sha1(str);
		assertTrue(afterHSA1.equals(result));
	}
}
