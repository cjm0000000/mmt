package lemon.yixin.test.biz;

import static org.junit.Assert.assertNotNull;
import lemon.yixin.fans.YiXinFansManager;
import lemon.yixin.fans.bean.YiXinFans;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class YiXinFansTest {
	private YiXinFansManager yxFansManager;
	private AbstractApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		yxFansManager = acx.getBean(YiXinFansManager.class);
		assertNotNull(yxFansManager);
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
		fans.setNick_name("");
		yxFansManager.saveFans(fans);
		//disable
		yxFansManager.disableFans(cust_id, wxid);
	}
	
}
