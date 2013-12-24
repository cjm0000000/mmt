package com.github.cjm0000000.mmt.yixin.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.cjm0000000.mmt.yixin.YiXin;

@RunWith(JUnit4.class)
public class YiXin_Test extends AbstractYiXinTester {
	
	@Before
	public void init(){
		YiXin.init();
	}
	
	@Override
	protected void defaultCase() {
		assertEquals("https://api.yixin.im/cgi-bin/token", YiXin.getCommonUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/create", YiXin.getCreateMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/get", YiXin.getSearchMenuUrl());
		assertEquals("https://api.yixin.im/cgi-bin/menu/delete", YiXin.getDeleteMenuUrl());
	}
}
