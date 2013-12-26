package lemon.shared.test.message.log;

import static org.junit.Assert.*;
import lemon.shared.message.log.MsgLog;
import lemon.shared.message.log.MsgLogManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.shared.test.AbstractTester;

public class MsgLogManagerTest extends AbstractTester {
	@Autowired
	private MsgLogManager msgLogManager;
	
	@Test
	public void saveRecvLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveRecvLog(log));
	}

	@Test
	public void saveSendLog(){
		MsgLog log = generateMsgLog();
		assertNotEquals(0, msgLogManager.saveSendLog(log));
	}
	
	private MsgLog generateMsgLog(){
		MsgLog log = new MsgLog();
		log.setCust_id(CUST_ID);
		log.setMsg("hello msg");
		log.setService_type(ServiceType.OTHER);
		log.setTimestamp(null);
		return log;
	}
}
