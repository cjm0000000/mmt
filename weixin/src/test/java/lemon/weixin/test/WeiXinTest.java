package lemon.weixin.test;

import static org.junit.Assert.assertEquals;
import lemon.weixin.config.WeiXin;
import lemon.weixin.test.base.BaseWeiXinTest;

import org.junit.Before;
import org.junit.Test;

public class WeiXinTest extends BaseWeiXinTest {
	
	@Before
	public void init(){
		WeiXin.init();
	}
	
	@Test
	public void testWeiXinProperties() {
		assertEquals("https://api.weixin.qq.com/cgi-bin/token", WeiXin.getCommonUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/create", WeiXin.getCreateMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/get", WeiXin.getSearchMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/delete", WeiXin.getDeleteMenuUrl());
	}
}
