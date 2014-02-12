package com.github.cjm0000000.mmt.web.test.log;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.web.log.LoginLog;
import com.github.cjm0000000.mmt.web.log.persistence.SysLogRepository;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;

public class SystemLog_Test extends AbstractWebTester{
	@Autowired
	private SysLogRepository logManager;
	
	@Test
	public void testLoginLog(){
		int user_id = 10;
		LoginLog available = new LoginLog();
		available.setLoginip("IP");
		available.setLoginstatus(Status.AVAILABLE);
		available.setRole_id(10);
		available.setUser_id(user_id);
		available.setUser_name("username");
		logManager.saveLoginLog(available);

		LoginLog unavailable = new LoginLog();
		unavailable.setLoginip("IP 2");
		unavailable.setLoginstatus(Status.UNAVAILABLE);
		unavailable.setRole_id(10);
		unavailable.setUser_id(user_id);
		unavailable.setUser_name("username 2");
		logManager.saveLoginLog(unavailable);
		
		List<LoginLog> logList = logManager.getLoginLog(user_id, 0, 20);
		assertNotNull(logList);
		for (LoginLog loginLog : logList) {
			assertNotNull(loginLog.getLoginstatus());
			System.out.println(loginLog.getLoginstatus()+"  "+loginLog.getLogintime());
		}
	}
}
