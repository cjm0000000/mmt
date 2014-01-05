package com.github.cjm0000000.mmt.web.test.interfaces;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuAPI;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

public class CustomMenu_Test extends AbstractWebTester {
  @Autowired
  @Qualifier("weiXinCustomMenuAPI")
  private CustomMenuAPI menuAPI;
  @Autowired
  private WeiXinConfigRepository wxConfigRepository;

  @Test
  public void testCreateMenu() {
    ReturnCode rCode = menuAPI.createMenus(wxConfigRepository.get(CUST_ID), "null");
    System.out.println(rCode.getErrcode() + " " + rCode.getErrmsg());
  }
}
