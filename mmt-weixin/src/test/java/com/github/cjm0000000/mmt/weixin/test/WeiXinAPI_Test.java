package com.github.cjm0000000.mmt.weixin.test;

import lemon.shared.api.MmtAPI;
import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.shared.access.Access;
import com.github.cjm0000000.mmt.weixin.WeiXin;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

public class WeiXinAPI_Test extends AbstractWeiXinTester {
	@Autowired @Qualifier("weiXinAPI")
	private MmtAPI api;
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String Welcome_msg = "Welcome";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = "lemon.weixin.biz.customer.SimpleWeiXinMsgProcessor";
	@Autowired
	private CustomerRepository customerMapper;
	@Autowired
	private WeiXinConfigRepository	wxConfigMapper;
	@Before
	public void init() {
		Customer cust = customerMapper.getCustomer(CUST_ID);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(CUST_ID);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus(Status.AVAILABLE);
			customerMapper.addCustomer(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		//add WeiXin configure
		WeiXinConfig cfg = wxConfigMapper.get(CUST_ID);
		if(null == cfg){
			cfg = new WeiXinConfig();
			cfg.setCust_id(CUST_ID);
			cfg.setToken(TOKEN);
			cfg.setApi_url(MMT_TOKEN);
			cfg.setWx_account("lemon_test");
			cfg.setAppid("");
			cfg.setSecret("");
			cfg.setBiz_class(bizClass);
			cfg.setSubscribe_msg(Subscribe_msg);
			cfg.setWelcome_msg(Welcome_msg);
			wxConfigMapper.save(cfg);
			assertNotEquals(cfg.getCust_id(), 0);
		}
		WeiXin.init();
		WeiXin.setConfig(cfg);
	}
	
	@Test
	public void verifySignature() {
		Access log = new Access();
		log.setEchostr("5921940693384818207");
		log.setNonce("1378809259");
		log.setSignature("d864452f69eccd3bc25c4ab94a1723cbba282ff6");
		log.setTimestamp_api("1377955356");
		log.setCust_id(1);
		log.setToken("QEQdsqo");

		boolean result = api.verifySignature(log);
		assertTrue(result);
	}
	
	@Test
	@Ignore
	public void getAcessToken(){
		String ss = api.getAcessToken(wxConfigMapper.get(CUST_ID));
		assertNotNull(ss);
	}

	@Override
	protected void defaultCase() {
		
	}
	
}
