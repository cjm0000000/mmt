package com.github.cjm0000000.mmt.web.test.interfaces;

import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuAPI;
import com.github.cjm0000000.mmt.shared.customer.Customer;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomerRepository;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;
import com.github.cjm0000000.mmt.weixin.WeiXinException;
import com.github.cjm0000000.mmt.weixin.api.passive.SimpleWeiXinMsgProcessor;
import com.github.cjm0000000.mmt.weixin.config.AccountType;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

/**
 * JUnit test cases for custom menu
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenu_Test extends AbstractWebTester {
  @Autowired
  @Qualifier("weiXinCustomMenuAPI")
  private CustomMenuAPI menuAPI;
  @Autowired
  private WeiXinConfigRepository wxConfigRepository;
  @Autowired
  private CustomerRepository custRepo;

  private int cust_id;

  @Before
  public void addCustomer() {
    // add customer
    Customer cust = new Customer();
    cust.setCust_name("Customer By menu_Test");
    cust.setMemo(UUID.randomUUID().toString());
    cust.setStatus(Status.AVAILABLE);
    int result = custRepo.addCustomer(cust);
    assertNotEquals(0, result);

    // add WeiXin configure
    WeiXinConfig cfg = new WeiXinConfig();
    cfg.setAccount_type(AccountType.FW);
    cfg.setApi_url("asdddddddaa");
    cfg.setAppid("appid");
    cfg.setBiz_class(SimpleWeiXinMsgProcessor.class.getName());
    cfg.setSecret("sec");
    cfg.setSubscribe_msg("msg");
    cfg.setWx_account("acceee");
    cfg.setCust_id(cust.getCust_id());
    result = wxConfigRepository.save(cfg);
    assertNotEquals(0, result);
    cust_id = cust.getCust_id();
  }

  @Test
  public void testCreateMenu() {
    try {
      ReturnCode rCode = menuAPI.createMenus(wxConfigRepository.get(cust_id), "null");
      System.out.println(rCode.getErrcode() + " " + rCode.getErrmsg());
    } catch (WeiXinException e) {
      e.printStackTrace();
    }
  }
}
