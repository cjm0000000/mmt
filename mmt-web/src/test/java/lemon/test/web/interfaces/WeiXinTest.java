package lemon.test.web.interfaces;

import lemon.shared.access.ReturnCode;
import lemon.shared.api.MmtAPI;
import lemon.test.web.base.BaseWebTest;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

public class WeiXinTest extends BaseWebTest {
	@Autowired @Qualifier("weiXinAPI")
	private MmtAPI wxApi;
	@Autowired
	private WeiXinConfigRepository wxConfigMapper;
	
	@Test
	@Ignore
	public void testCreateMenu(){
		WeiXinConfig cfg = wxConfigMapper.get(1);
		ReturnCode rCode = wxApi.createMenus(cfg, "null");
		System.out.println(rCode.getErrcode()+ " " + rCode.getErrmsg());
	}
}
