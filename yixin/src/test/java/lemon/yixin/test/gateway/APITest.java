package lemon.yixin.test.gateway;

import static org.junit.Assert.assertNotNull;
import lemon.shared.api.MmtAPI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class APITest {
	private MmtAPI api;
	private AbstractApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		api = (MmtAPI) acx.getBean(MmtAPI.class);
		assertNotNull(api);
	}
	@After
	public void destory(){
		acx.close();
	}

	@Test
	public void apiTest(){
		//api.verifySignature(null);
	}
}
