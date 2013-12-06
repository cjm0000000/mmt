package lemon.test.web.interfaces;

import lemon.shared.access.ReturnCode;
import lemon.shared.api.MmtAPI;
import lemon.test.web.base.BaseWebTest;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class WeiXinTest extends BaseWebTest {
	@Autowired @Qualifier("weiXinAPI")
	private MmtAPI wxApi;
	@Autowired
	private WXConfigMapper wxConfigMapper;
	
	@Test
	@Ignore
	public void testCreateMenu(){
		WeiXinConfig cfg = wxConfigMapper.get(1);
		ReturnCode rCode = wxApi.createMenus(cfg, "null");
		System.out.println(rCode.getErrcode()+ " " + rCode.getErrmsg());
	}
}
