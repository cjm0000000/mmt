package com.github.cjm0000000.mmt.weixin.test.config;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.shared.customer.Customer;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomerRepository;
import com.github.cjm0000000.mmt.shared.toolkit.EqualsUtil;
import com.github.cjm0000000.mmt.weixin.api.passive.SimpleWeiXinMsgProcessor;
import com.github.cjm0000000.mmt.weixin.config.AccountType;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;
import com.github.cjm0000000.mmt.weixin.test.AbstractWeiXinTester;

/**
 * JUnit test case for WeiXin configure repository
 * @author lemon
 * @version 1.0
 *
 */
public class WeiXinConfigRepo_Test extends AbstractWeiXinTester {
  @Autowired
  private WeiXinConfigRepository configMapper;
  @Autowired
  private CustomerRepository custMapper;

  @Override
  protected void defaultCase() {
    Customer cust = custMapper.getCustomer(CUST_ID);
    if (null == cust) {
      cust = new Customer();
      cust.setCust_name("JUnit Test Customer " + UUID.randomUUID().toString());
      cust.setMemo("JUnit description " + UUID.randomUUID().toString());
      cust.setStatus(Status.AVAILABLE);
      assertNotEquals(0, custMapper.addCustomer(cust));
    }

    WeiXinConfig cfg = configMapper.get(CUST_ID);
    if (null != cfg) assertNotEquals(0, configMapper.delete(CUST_ID));
    cfg = new WeiXinConfig();
    cfg.setCust_id(CUST_ID);
    cfg.setToken(UUID.randomUUID().toString());
    cfg.setWx_account(UUID.randomUUID().toString());
    cfg.setSubscribe_msg("Welcome to subscribe Junit Test ");
    cfg.setWelcome_msg("业务咨询请按1");
    cfg.setApi_url(UUID.randomUUID().toString());
    cfg.setAppid(UUID.randomUUID().toString());
    cfg.setSecret(UUID.randomUUID().toString());
    cfg.setBiz_class(SimpleWeiXinMsgProcessor.class.getName());
    cfg.setAccount_type(AccountType.DY);
    assertNotEquals(0, configMapper.save(cfg));

    List<WeiXinConfig> list = configMapper.availableList();
    assertNotNull(list);
    for (WeiXinConfig weiXinConfig : list) {
      assertNotNull(weiXinConfig);
      if (weiXinConfig.getCust_id() == CUST_ID) 
        assertTrue(EqualsUtil.deepEquals(weiXinConfig, cfg));
    }
  }

}
