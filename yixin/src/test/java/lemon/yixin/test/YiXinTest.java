package lemon.yixin.test;

import static org.junit.Assert.*;
import lemon.yixin.config.YiXin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class YiXinTest {
	
	@Before
	public void init(){
		YiXin.init();
		
	}
	@Test
	public void testWeiXinProperties() {
		assertEquals("https://api.yixin.im/cgi-bin/token?grant_type=#{MMT}&appid=#{MMT}&secret=#{MMT}", YiXin.getCommonUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/create?access_token=#{MMT}", YiXin.getCreateMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/get?access_token=#{MMT}", YiXin.getSearchMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/delete?access_token=#{MMT}", YiXin.getDeleteMenuUrl());
	}
}
