package com.github.cjm0000000.shared.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.github.cjm0000000.mmt.shared.MMTContext;

public class MMTContext_Test extends AbstractTester {
	
	@Test
	public void testMMT(){
		ApplicationContext acx = MMTContext.getApplicationContext();
		assertNotNull(acx.getBean(MMTContext.class));
	}
}
