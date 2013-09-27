package lemon.yixin.test.biz;

import lemon.shared.access.bean.SiteAccess;
import lemon.shared.api.MmtAPI;
import lemon.shared.toolkit.secure.SecureUtil;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YiXinAPITest {
	private MmtAPI api;
	private AbstractApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		api = (MmtAPI) acx.getBean(MmtAPI.class);
		assertNotNull(api);
	}
	@After
	public void destory(){
		acx.close();
	}
	@Test
	public void testSignature() {
		SiteAccess sa = new SiteAccess();
		sa.setEchostr("5921940693384818207");
		sa.setNonce("1378809259");
		sa.setSignature("d864452f69eccd3bc25c4ab94a1723cbba282ff6");
		sa.setTimestamp("1377955356");
		sa.setCust_id(1);
		sa.setToken("QEQdsqo");

		boolean result = api.verifySignature(sa);
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
