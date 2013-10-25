package lemon.shared.test.fans;

import static org.junit.Assert.assertNotNull;
import lemon.shared.entity.Status;
import lemon.shared.fans.Fans;
import lemon.shared.fans.FansManager;
import lemon.shared.service.ServiceType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class FansTest {
	private FansManager fansManager;
	private ApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		fansManager = acx.getBean(FansManager.class);
		assertNotNull(fansManager);
	}
	
	@Test
	public void addFans(){
		String user_id = "ot9x4jpm4x_rBrqacQ8hzikL9D-M";
		int cust_id = 10;
		//save
		Fans fans = new Fans();
		fans.setCust_id(cust_id);
		fans.setUser_id(user_id);
		fans.setService_type(ServiceType.OTHER);
		fans.setStatus(Status.AVAILABLE);
		fans.setNick_name("Tom");
		fansManager.saveFans(fans);
		//disable
		fansManager.disableFans(cust_id,ServiceType.OTHER, user_id);
	}
	
}
