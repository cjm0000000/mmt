package com.github.cjm0000000.shared.test.message.log;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.log.MsgLog;
import com.github.cjm0000000.mmt.shared.message.log.MsgLogManager;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test case for message log
 * @author lemon
 * @version 1.0
 *
 */
public class MsgLogManager_Test extends AbstractTester {
	@Autowired
	private MsgLogManager msgLogManager;
	
	@Override
	protected void defaultCase() {
		saveRecvLog();
	}
	
	/**
	 * save send log
	 */
	@Test
	public void saveSendLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveSendLog(log));
	}
	
	/**
	 * save receive log
	 */
	private void saveRecvLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveRecvLog(log));
	}
	
	/**
	 * generate message log
	 * @return
	 */
	private MsgLog generateMsgLog(){
		MsgLog log = new MsgLog();
		log.setCust_id(CUST_ID);
		log.setMsg("hello msg");
		log.setService_type(ServiceType.OTHER);
		log.setTimestamp(null);
		return log;
	}

}
