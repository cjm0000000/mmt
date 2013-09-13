package lemon.yixin.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.entity.Customer;
import lemon.shared.entity.Status;
import lemon.shared.mapper.CustomerMapper;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.dao.YXConfigMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YiXinConfigMapperTest {
	private YXConfigMapper configMapper;
	private CustomerMapper custMapper;
	private AbstractApplicationContext acx;
	private static Log logger = LogFactory.getLog(YiXinConfigMapperTest.class);
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		configMapper = (YXConfigMapper) acx.getBean(YXConfigMapper.class);
		custMapper = (CustomerMapper) acx.getBean(CustomerMapper.class);
		assertNotNull(configMapper);
		assertNotNull(custMapper);
	}
	@After
	public void destory(){
		acx.close();
	}
	@Test
	public void crud(){
		int cust_id = 10;
		Customer cust = custMapper.getCustomer(cust_id);
		if(null == cust){
			cust = new Customer();
			cust.setCust_name("JUnit Test Customer");
			cust.setMemo("JUnit Test");
			cust.setStatus(Status.AVAILABLE);
			custMapper.addCustomer(cust);
			cust_id = cust.getCust_id();
		}
		assertNotEquals(0, cust_id);
		
		YiXinConfig cfg = configMapper.get(cust_id);
		if(null != cfg){
			configMapper.delete(cust_id);
		}
		cfg = new YiXinConfig();
		cfg.setCust_id(cust_id);
		cfg.setToken("Junit Test Token");
		cfg.setYx_account("Junit Test YiXin Account");
		cfg.setSubscribe_msg("Welcome to subscribe Junit Test");
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
