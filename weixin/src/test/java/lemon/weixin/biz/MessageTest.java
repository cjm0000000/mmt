package lemon.weixin.biz;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lemon.shared.api.MmtAPI;
import lemon.shared.common.Customer;
import lemon.shared.mapper.CustomerMapper;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.biz.parser.TextMsgParser;
import lemon.weixin.biz.parser.VideoMsgParser;
import lemon.weixin.biz.parser.VoiceMsgParser;
import lemon.weixin.dao.WXConfigMapper;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MessageTest {
	private MmtAPI api;
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = "lemon.weixin.biz.LemonMessageBiz";
	private final int cust_id = 100;
	private WeiXinMsgHelper msgHelper;
	private ApplicationContext acx;
	private CustomerMapper customerMapper;
	private WXConfigMapper	wxConfigMapper;
	@Before
	public void init() {
		//FIXME Refactoring to support subscribe
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		api = acx.getBean(MmtAPI.class);
		msgHelper = acx.getBean(WeiXinMsgHelper.class);
		customerMapper = acx.getBean(CustomerMapper.class);
		wxConfigMapper = acx.getBean(WXConfigMapper.class);
		assertNotNull(api);
		assertNotNull(msgHelper);
		assertNotNull(customerMapper);
		assertNotNull(wxConfigMapper);
		
		//add customer
		Customer cust = customerMapper.get(cust_id);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(cust_id);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus("1");
			customerMapper.save(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		
		//add WeiXin configure
		WeiXinConfig cfg = wxConfigMapper.get(cust_id);
		if(null == cfg){
			cfg = new WeiXinConfig();
			cfg.setCust_id(cust_id);
			cfg.setToken(TOKEN);
			cfg.setApi_url(MMT_TOKEN);
			cfg.setWx_account("lemon_test");
			cfg.setAppid("");
			cfg.setSecret("");
			cfg.setBiz_class(bizClass);
			cfg.setSubscribe_msg(Subscribe_msg);
			wxConfigMapper.save(cfg);
			assertNotEquals(cfg.getCust_id(), 0);
		}
		WeiXin.init();
		WeiXin.setConfig(cfg);
	}
	@Test
	public void testSaveTextMsg(){
		String txtMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378050293</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ss]]></Content><MsgId>5918680940678217795</MsgId></xml>";
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(txtMsg);
		msgHelper.saveRecvTextMsg(msg);
	}
	@Test
	public void parserMsgType() throws JDOMException, IOException{
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378040271</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5918637896515977279</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		Assert.assertTrue("voice".equals(msgType.getValue()));
	}
	@Test
	public void textMsgTest(){
		String txtMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378050293</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ss]]></Content><MsgId>5918680940678217795</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, txtMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "Lemon Text message replay.");
	}
	@Test
	//FIXME subscribeTest
	public void subscribeTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378090586</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), Subscribe_msg);
	}
	@Test
	@Ignore
	//FIXME unsubscribe test
	public void unsubscribe(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378090569</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		assertEquals(result, null);
	}
	@Test
	public void linkMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377753855909</CreateTime><MsgType><![CDATA[link]]></MsgType><MsgId>1024102410241024</MsgId><Title><![CDATA[Link \"TEST\" Title]]></Title><Description><![CDATA[Link DESC]]></Description><Url><![CDATA[http://www.163.com/s/a/d/f/a]]></Url></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "Lemon Link message replay.");
	}
	@Test
	public void imageMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378091075</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/QXd6JDcZQ1ls9utpyRLS45ib4XPBm8jLD27oeCgOrlsjgJuUictQTHXw/0]]></PicUrl><MsgId>5918856098034483283</MsgId><MediaId><![CDATA[ZTjFiu7uLSfqupgRn2z4uZT8JqulZXKntm6ERVXrFtcppQOTF9x8Ow-cCb1yoUoy]]></MediaId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "Lemon Image message replay.");
	}
	@Test
	public void locationMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378091153</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>30.278790</Location_X><Location_Y>120.145454</Location_Y><Scale>20</Scale><Label><![CDATA[???????????????????????26? ????: 310000]]></Label><MsgId>5918856433041932373</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "Lemon Location message replay.");
	}
	
	@Test
	public void videoMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1377961745</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[Iy6-lX7dSLa45ztf6AVdjTDcHTWLk3C80VHMGi40HfI1CnpPqixCb6FUJ2ZG4wNd]]></MediaId><ThumbMediaId><![CDATA[gJNpZwX41lZ651onCiBzaYkYOTrqDC_v6oBY9TNocYCMWHG7Zsp67-jq-NRQS1Uk]]></ThumbMediaId><MsgId>5918300629914091537</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		VideoMessage msg = acx.getBean(VideoMsgParser.class).toMsg(result);
		assertEquals(msg.getThumbMediaId(), "gJNpZwX41lZ651onCiBzaYkYOTrqDC_v6oBY9TNocYCMWHG7Zsp67-jq-NRQS1Uk");
		assertEquals(msg.getMediaId(), "Iy6-lX7dSLa45ztf6AVdjTDcHTWLk3C80VHMGi40HfI1CnpPqixCb6FUJ2ZG4wNd");
	}
	@Test
	public void voiceMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378040271</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5918637896515977279</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		VoiceMessage msg = acx.getBean(VoiceMsgParser.class).toMsg(result);
		assertEquals(msg.getFormat(), "amr");
		assertEquals(msg.getMediaId(), "PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj");
		assertEquals(msg.getRecognition(), "");
	}
	
}
