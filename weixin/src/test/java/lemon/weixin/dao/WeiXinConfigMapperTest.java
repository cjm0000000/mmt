package lemon.weixin.dao;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.common.Customer;
import lemon.shared.mapper.CustomerMapper;
import lemon.weixin.bean.WeiXinConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinConfigMapperTest {
	private WeiXinConfigMapper configMapper;
	private CustomerMapper custMapper;
	private static Log logger = LogFactory.getLog(WeiXinConfigMapperTest.class);
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		configMapper = (WeiXinConfigMapper) acx.getBean(WeiXinConfigMapper.class);
		custMapper = (CustomerMapper) acx.getBean(CustomerMapper.class);
		assertNotNull(configMapper);
		assertNotNull(custMapper);
	}
	@Test
	public void crud(){
		int cust_id = 10;
		Customer cust = custMapper.get(cust_id);
		if(null == cust){
			cust = new Customer();
			cust.setCust_name("JUnit Test Customer");
			cust.setMemo("JUnit Test");
			cust.setStatus("1");
			custMapper.save(cust);
			cust_id = cust.getCust_id();
		}
		assertNotEquals(0, cust_id);
		
		WeiXinConfig cfg = configMapper.get(cust_id);
		if(null != cfg){
			configMapper.delete(cust_id);
		}
		cfg = new WeiXinConfig();
		cfg.setCust_id(cust_id);
		cfg.setToken("Junit Test Token");
		cfg.setWx_account("Junit Test WeiXin Account");
		configMapper.save(cfg);
		List<WeiXinConfig> list = configMapper.activeList();
		for (WeiXinConfig weiXinConfig : list) {
			assertNotNull(weiXinConfig.getTimestamp());
			logger.debug(weiXinConfig);
		}
	}
}
