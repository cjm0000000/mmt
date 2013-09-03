package lemon.weixin.biz;

import static org.junit.Assert.assertNotNull;
import lemon.weixin.bean.message.WeiXinFans;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinFansTest {
	private WeiXinFansManager wxFansManager;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		wxFansManager = acx.getBean(WeiXinFansManager.class);
		assertNotNull(wxFansManager);
	}
	
	@Test
	public void addFans(){
		String wxid = "ot9x4jpm4x_rBrqacQ8hzikL9D-M";
		int cust_id = 10;
		//save
		WeiXinFans fans = new WeiXinFans();
		fans.setCust_id(cust_id);
		fans.setWxid(wxid);
		wxFansManager.saveFans(fans);
		//disable
		wxFansManager.disableFans(cust_id, wxid);
	}
	
}
