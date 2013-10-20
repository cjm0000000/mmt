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
	public void testYiXinProperties() {
		assertEquals("https://api.yixin.im/cgi-bin/token", YiXin.getCommonUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/create", YiXin.getCreateMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/get", YiXin.getSearchMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/delete", YiXin.getDeleteMenuUrl());
	}
}
