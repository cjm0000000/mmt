package lemon.test.web.interfaces;

import static org.junit.Assert.*;
import lemon.shared.access.ReturnCode;
import lemon.shared.api.MmtAPI;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeiXinTest {
	private ApplicationContext acx;
	private MmtAPI wxApi;
	private WXConfigMapper wxConfigMapper;
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		wxApi = (MmtAPI) acx.getBean("weiXinAPI");
		assertNotNull(wxApi);
		wxConfigMapper = acx.getBean(WXConfigMapper.class);
		assertNotNull(wxConfigMapper);
	}
	
	@Test
	@Ignore
	public void testCreateMenu(){
		WeiXinConfig cfg = wxConfigMapper.get(1);
		ReturnCode rCode = wxApi.createMenus(cfg, "null");
		System.out.println(rCode.getErrcode()+ " " + rCode.getErrmsg());
	}
}
