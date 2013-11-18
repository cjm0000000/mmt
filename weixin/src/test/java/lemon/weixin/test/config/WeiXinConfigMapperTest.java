package lemon.weixin.test.config;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.shared.toolkit.EqualsUtil;
import lemon.weixin.config.bean.AccountType;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;
import lemon.weixin.message.processor.SimpleWeiXinMsgProcessor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinConfigMapperTest {
	private WXConfigMapper configMapper;
	private CustomerRepository custMapper;
	private ApplicationContext acx;
	private static final int CUST_ID = -5743;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		configMapper = (WXConfigMapper) acx.getBean(WXConfigMapper.class);
		custMapper = (CustomerRepository) acx.getBean(CustomerRepository.class);
		assertNotNull(configMapper);
		assertNotNull(custMapper);
	}
	
	@Test
	public void crud(){
		Customer cust = custMapper.getCustomer(CUST_ID);
		if(null == cust){
			cust = new Customer();
			cust.setCust_name("JUnit Test Customer " + UUID.randomUUID().toString());
			cust.setMemo("JUnit description " + UUID.randomUUID().toString());
			cust.setStatus(Status.AVAILABLE);
			assertNotEquals(0, custMapper.addCustomer(cust));
		}
		
		WeiXinConfig cfg = configMapper.get(CUST_ID);
		if(null != cfg)
			assertNotEquals(0, configMapper.delete(CUST_ID));
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
			if(weiXinConfig.getCust_id() == CUST_ID){
				assertTrue(EqualsUtil.deepEquals(weiXinConfig, cfg));
			}
		}
	}
	
}
