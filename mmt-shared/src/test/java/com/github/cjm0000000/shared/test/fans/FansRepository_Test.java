package com.github.cjm0000000.shared.test.fans;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.fans.Fans;
import com.github.cjm0000000.mmt.shared.fans.FansManager;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test case for fans repository
 * @author lemon
 * @version 1.1
 *
 */
public class FansRepository_Test extends AbstractTester {
	@Autowired
	private FansManager fansManager;
	
	@Override
	public void defaultCase() {
		addFans();
	}
	
	private void addFans(){
		assertNotNull(fansManager);
		String user_id = "ot9x4jpm4x_rBrqacQ8hzikL9D-M";
		//save
		Fans fans = new Fans();
		fans.setCust_id(CUST_ID);
		fans.setUser_id(user_id);
		fans.setService_type(ServiceType.OTHER);
		fans.setStatus(Status.AVAILABLE);
		fans.setNick_name("Tom");
		fansManager.saveFans(fans);
		//disable
		fansManager.disableFans(CUST_ID,ServiceType.OTHER, user_id);
	}
}
