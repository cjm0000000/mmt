package lemon.shared;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MMTContextTest {
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		assertEquals(acx, MMTContext.getApplicationContext());
	}
	@Test
	public void testMMT(){
		ApplicationContext acx = MMTContext.getApplicationContext();
		assertNotNull(acx.getBean(MMTContext.class));
	}
}
