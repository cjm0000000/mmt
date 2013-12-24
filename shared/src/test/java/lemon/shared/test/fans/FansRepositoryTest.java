package lemon.shared.test.fans;

import static org.junit.Assert.*;
import lemon.shared.fans.Fans;
import lemon.shared.fans.FansManager;
import lemon.shared.service.ServiceType;
import lemon.shared.test.base.BaseMmtTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.config.Status;

public class FansRepositoryTest extends BaseMmtTest {
	@Autowired
	private FansManager fansManager;
	
	@Test
	public void addFans(){
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
