package lemon.yixin.test.config;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.config.mapper.YXConfigMapper;
import lemon.yixin.test.base.BaseYiXinTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;

public class YiXinConfigMapperTest extends BaseYiXinTest {
	@Autowired
	private YXConfigMapper configMapper;
	@Autowired
	private CustomerRepository custMapper;
	private static Log logger = LogFactory.getLog(YiXinConfigMapperTest.class);
	private int CUST_ID = -5745;
	
	@Test
	public void crud(){
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
