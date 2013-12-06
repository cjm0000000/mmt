package lemon.shared.test.message.log;

import static org.junit.Assert.*;
import lemon.shared.message.log.MsgLog;
import lemon.shared.message.log.MsgLogManager;
import lemon.shared.service.ServiceType;
import lemon.shared.test.base.BaseMmtTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MsgLogManagerTest extends BaseMmtTest {
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
