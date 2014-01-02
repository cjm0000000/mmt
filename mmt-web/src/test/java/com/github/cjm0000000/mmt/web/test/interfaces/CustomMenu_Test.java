package com.github.cjm0000000.mmt.web.test.interfaces;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuAPI;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

public class CustomMenu_Test extends AbstractWebTester {
  @Autowired
  @Qualifier("weiXinAPI")
  private CustomMenuAPI menuAPI;
  @Autowired
  private WeiXinConfigRepository wxConfigMapper;

  @Test
  public void testCreateMenu() {
    ReturnCode rCode = menuAPI.createMenus("null");
    System.out.println(rCode.getErrcode() + " " + rCode.getErrmsg());
  }
}
