package lemon.weixin;

import static org.junit.Assert.*;
import lemon.weixin.config.WeiXin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WeiXinTest {
	
	@Before
	public void init(){
		WeiXin.init();
		
	}
	@Test
	public void testWeiXinProperties() {
		assertEquals("https://api.weixin.qq.com/cgi-bin/token", WeiXin.getCommonUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=#{MMT}", WeiXin.getCreateMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=#{MMT}", WeiXin.getSearchMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=#{MMT}", WeiXin.getDeleteMenuUrl());
	}
}
