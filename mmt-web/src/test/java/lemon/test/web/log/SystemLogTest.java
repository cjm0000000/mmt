package lemon.test.web.log;

import java.util.List;

import lemon.shared.config.Status;
import lemon.test.web.base.BaseWebTest;
import lemon.web.log.bean.LoginLog;
import lemon.web.log.mapper.SystemLogManager;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemLogTest extends BaseWebTest{
	@Autowired
	private SystemLogManager logManager;
	
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
