package com.github.cjm0000000.shared.test;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.github.cjm0000000.mmt.shared.MMTContext;

/**
 * 测试MMT容器
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MMTContext_Test extends AbstractTester {
  @Autowired
  private MMTContext context;

  @Override
  protected void defaultCase() {
    ApplicationContext acx = context.getApplicationContext();
    assertNotNull(acx.getBean(MMTContext.class));
  }
}
