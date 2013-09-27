package lemon.weixin.biz;

import lemon.shared.access.bean.SiteAccess;
import lemon.shared.api.MmtAPI;
import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.Status;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.dao.WXConfigMapper;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinAPITest {
	private MmtAPI api;
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String Welcome_msg = "Welcome";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = "lemon.weixin.biz.customer.SimpleWeiXinMsgProcessor";
	private final int cust_id = 100;
	private ApplicationContext acx;
	private CustomerMapper customerMapper;
	private WXConfigMapper	wxConfigMapper;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		api = acx.getBean(WeiXinAPI.class);
		customerMapper = acx.getBean(CustomerMapper.class);
		wxConfigMapper = acx.getBean(WXConfigMapper.class);
		assertNotNull(api);
		assertNotNull(customerMapper);
		assertNotNull(wxConfigMapper);
		
		Customer cust = customerMapper.getCustomer(cust_id);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(cust_id);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus(Status.AVAILABLE);
			customerMapper.addCustomer(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		
		//add WeiXin configure
		WeiXinConfig cfg = wxConfigMapper.get(cust_id);
		if(null == cfg){
			cfg = new WeiXinConfig();
			cfg.setCust_id(cust_id);
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
	@Ignore
	public void verifySignature() {
		SiteAccess log = new SiteAccess();
		log.setEchostr("5921940693384818207");
		log.setNonce("1378809259");
		log.setSignature("d864452f69eccd3bc25c4ab94a1723cbba282ff6");
		log.setTimestamp("1377955356");
		log.setCust_id(1);
		log.setToken("QEQdsqo");

		boolean result = api.verifySignature(log);
		assertTrue(result);
	}
	
	@Test
	@Ignore
	public void getAcessToken(){
		String ss = api.getAcessToken(MMT_TOKEN);
		System.out.println(ss);
	}
	
}
