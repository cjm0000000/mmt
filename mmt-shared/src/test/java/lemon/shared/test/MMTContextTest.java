package lemon.shared.test;

import static org.junit.Assert.*;
import lemon.shared.MMTContext;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.github.cjm0000000.shared.test.AbstractTester;

public class MMTContextTest extends AbstractTester {
	
	@Test
	public void testMMT(){
		ApplicationContext acx = MMTContext.getApplicationContext();
		assertNotNull(acx.getBean(MMTContext.class));
	}
}
