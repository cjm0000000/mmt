package lemon.yixin.test.biz;

import static org.junit.Assert.assertNotNull;
import lemon.yixin.bean.YiXinFans;
import lemon.yixin.biz.YiXinFansManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YiXinFansTest {
	private YiXinFansManager wxFansManager;
	private AbstractApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		wxFansManager = acx.getBean(YiXinFansManager.class);
		assertNotNull(wxFansManager);
	}
	
	@After
	public void destory(){
		acx.close();
	}
	
	@Test
	public void addFans(){
		String wxid = "ot9x4jpm4x_rBrqacQ8hzikL9D-M";
		int cust_id = 10;
		//save
		YiXinFans fans = new YiXinFans();
		fans.setCust_id(cust_id);
		fans.setYxid(wxid);
		wxFansManager.saveFans(fans);
		//disable
		wxFansManager.disableFans(cust_id, wxid);
	}
	
}
