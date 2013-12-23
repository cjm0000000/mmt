package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;
import com.github.cjm0000000.mmt.core.test.MmtTestBase;

/**
 * template unit tester
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractMsgTester extends MmtTestBase {
	/**
	 * get message instance
	 * @return
	 */
	protected abstract SimpleMessage getMsgInstance();
	
	/**
	 * set special field values
	 * @param msg
	 */
	protected abstract void setSpecFields(SimpleMessage original);
	
	/**
	 * verify special field
	 * @param original
	 * @param json
	 */
	protected abstract SimpleMessage verifySpecFields(SimpleMessage original, String json);
	
	@Test
	public void run(){
		test();
	}
	
	/**
	 * set the message's fields with initialize value
	 * @param service_type
	 * @param original
	 */
	private void setCommonFields(SimpleMessage original){
		original.setCust_id(CUST_ID);
		original.setId(0);
		original.setToUser("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
	}
	
	/**
	 * Test case
	 */
	private void test(){
		SimpleMessage original = getMsgInstance();
		setCommonFields(original);
		setSpecFields(original);
		String json = MmtJSONParser.toJSON(original);
		//verify special field
		SimpleMessage after = verifySpecFields(original, json);
		//verify common field
		verifyCommonFields(original, after);
	}
	
	/**
	 * verify common field
	 * @param original
	 * @param after
	 */
	private void verifyCommonFields(SimpleMessage original, SimpleMessage after){
		assertNotNull(after);
		assertEquals(after.getMsgType(), original.getMsgType());
		assertEquals(after.getToUser(), original.getToUser());
	}
}
