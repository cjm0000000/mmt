package lemon.yixin.test;

import lemon.shared.access.Access;
import lemon.shared.api.MmtAPI;
import lemon.shared.toolkit.secure.SecureUtil;
import lemon.yixin.test.base.BaseYiXinTest;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class YiXinAPITest extends BaseYiXinTest {
	@Autowired @Qualifier("yiXinAPI")
	private MmtAPI api;

	@Test
	public void testSignature() {
		Access sa = new Access();
		sa.setEchostr("5921940693384818207");
		sa.setNonce("1378809259");
		sa.setSignature("d864452f69eccd3bc25c4ab94a1723cbba282ff6");
		sa.setTimestamp_api("1377955356");
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
