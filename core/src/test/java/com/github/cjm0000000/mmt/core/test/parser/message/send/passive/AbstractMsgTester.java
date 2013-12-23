package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.test.MmtTestBase;

/**
 * template unit tester
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractMsgTester extends MmtTestBase {
	private static final Logger logger = Logger.getLogger(AbstractMsgTester.class);
	
	/**
	 * set special field values
	 * @param msg
	 */
	protected abstract void setSpecFields(SimpleMessageService original);
	
	/**
	 * get message instance
	 * @return
	 */
	protected abstract SimpleMessageService getMsgInstance();

	@Test
	public void run(){
		test();
	}
	
	private final void test(){
		SimpleMessageService original = getMsgInstance();
		setCommonFields(original);
		setSpecFields(original);
		String xml = MmtXMLParser.toXML(original);
		assertNotNull(xml);
		logger.debug(xml);
	}
	
	/**
	 * set the message's fields with initialize value
	 * @param service_type
	 * @param original
	 */
	private void setCommonFields(SimpleMessageService original){
		original.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
		original.setCust_id(CUST_ID);
		original.setFromUserName("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		original.setId(0);
		original.setToUserName("gh_de370ad657cf");
	}
}
