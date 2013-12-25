package com.github.cjm0000000.mmt.weixin.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import com.github.cjm0000000.mmt.weixin.WeiXin;

public class WeiXin_Test extends AbstractWeiXinTester {
	
	@Before
	public void init(){
		WeiXin.init();
	}

	@Override
	protected void defaultCase() {
		assertEquals("https://api.weixin.qq.com/cgi-bin/token", WeiXin.getCommonUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/create", WeiXin.getCreateMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/get", WeiXin.getSearchMenuUrl());
		assertEquals("https://api.weixin.qq.com/cgi-bin/menu/delete", WeiXin.getDeleteMenuUrl());
	}
}
