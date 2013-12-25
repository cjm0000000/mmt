package com.github.cjm0000000.mmt.yixin.test.config;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;
import com.github.cjm0000000.mmt.yixin.config.persistence.YiXinConfigRepository;
import com.github.cjm0000000.mmt.yixin.test.AbstractYiXinTester;

public class YiXinConfigRepo_Test extends AbstractYiXinTester {
	@Autowired
	private YiXinConfigRepository configMapper;
	@Autowired
	private CustomerRepository custMapper;
	private static Log logger = LogFactory.getLog(YiXinConfigRepo_Test.class);
	private int CUST_ID = -5745;
	@Override
	protected void defaultCase() {
		Customer cust = custMapper.getCustomer(CUST_ID);
		if(null == cust){
			cust = new Customer();
			cust.setCust_name("JUnit Test Customer");
			cust.setMemo("JUnit Test");
			cust.setStatus(Status.AVAILABLE);
			custMapper.addCustomer(cust);
			CUST_ID = cust.getCust_id();
		}
		assertNotEquals(0, CUST_ID);
		
		YiXinConfig cfg = configMapper.get(CUST_ID);
		if(null != cfg){
			configMapper.delete(CUST_ID);
		}
		cfg = new YiXinConfig();
		cfg.setCust_id(CUST_ID);
		cfg.setToken("Junit Test Token");
		cfg.setYx_account("Junit Test YiXin Account");
		cfg.setSubscribe_msg("Welcome to subscribe Junit Test");
		cfg.setWelcome_msg("业务咨询请按1");
		cfg.setApi_url("ASDLADLKJFWQ");
		cfg.setAppid("APPID");
		cfg.setSecret("secret");
		cfg.setBiz_class("com.com.XXX");
		configMapper.save(cfg);
		List<YiXinConfig> list = configMapper.availableList();
		for (YiXinConfig yiXinConfig : list) {
			assertNotNull(yiXinConfig.getTimestamp());
			logger.debug(yiXinConfig);
		}
	}
	
}
