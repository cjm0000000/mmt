package lemon.shared.test;

import static org.junit.Assert.*;
import lemon.shared.MMTContext;
import lemon.shared.test.base.BaseMmtTest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class MMTContextTest extends BaseMmtTest {
	
	@Test
	public void testMMT(){
		ApplicationContext acx = MMTContext.getApplicationContext();
		assertNotNull(acx.getBean(MMTContext.class));
	}
}
