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
	private MMTContext content;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		content = acx.getBean(MMTContext.class);
		assertEquals(acx, content.getApplicationContext());
	}
	@Test
	public void testMMT(){
		ApplicationContext acx = content.getApplicationContext();
		assertNotNull(acx.getBean(MMTContext.class));
	}
}
