package lemon.test.web.crm.yxapi;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lemon.shared.api.MmtAPI;
import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.entity.Status;
import lemon.yixin.YiXin;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.bean.message.TextMessage;
import lemon.yixin.biz.YiXinMsgHelper;
import lemon.yixin.biz.parser.TextMsgParser;
import lemon.yixin.dao.YXConfigMapper;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MMT_YiXin_MsgTest {
	private MmtAPI api;
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String Welcome_msg = "Welcome to Subscribe Lemon Test.";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = "lemon.web.crm.yxapi.MMT_YiXin_MsgProcessor";
	private final int cust_id = 201;
	private YiXinMsgHelper msgHelper;
	private ApplicationContext acx;
	private CustomerMapper customerMapper;
	private YXConfigMapper	wxConfigMapper;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		api = (MmtAPI) acx.getBean("yiXinAPI");
		msgHelper = acx.getBean(YiXinMsgHelper.class);
		customerMapper = acx.getBean(CustomerMapper.class);
		wxConfigMapper = acx.getBean(YXConfigMapper.class);
		assertNotNull(api);
		assertNotNull(msgHelper);
		assertNotNull(customerMapper);
		assertNotNull(wxConfigMapper);
		
		//add customer
		Customer cust = customerMapper.getCustomer(cust_id);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(cust_id);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus(Status.AVAILABLE);
			customerMapper.addCustomer(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		
		//add YiXin configure
		YiXinConfig cfg = wxConfigMapper.get(cust_id);
		if(null == cfg){
			cfg = new YiXinConfig();
			cfg.setCust_id(cust_id);
			cfg.setToken(TOKEN);
			cfg.setApi_url(MMT_TOKEN);
			cfg.setYx_account("lemon_test");
			cfg.setAppid("");
			cfg.setSecret("");
			cfg.setBiz_class(bizClass);
			cfg.setSubscribe_msg(Subscribe_msg);
			cfg.setWelcome_msg(Welcome_msg);
			wxConfigMapper.save(cfg);
			assertNotEquals(cfg.getCust_id(), 0);
		}
		YiXin.init();
		YiXin.setConfig(cfg);
	}
	
	@Test
	public void parserMsgType() throws JDOMException, IOException{
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379128652</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>。。。</Content></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes("UTF-8"));
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		Assert.assertTrue("text".equals(msgType.getValue()));
	}
	@Test
	public void textMsgTest(){
		String txtMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379128652</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>。。。</Content></xml>";
		String result = api.processMsg(MMT_TOKEN, txtMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "Welcome to Subscribe Lemon Test.");
	}
	@Test
	public void subscribeTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377682037</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[0dfsafkqwnriksdk]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), Subscribe_msg);
	}
	
	@Test
	public void unsubscribe(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377682037</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[0dfsafkqwnriksdk]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		assertNull(result);
	}
	@Test
	public void linkMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377753855</CreateTime><MsgType><![CDATA[link]]></MsgType><MsgId>1024102410241024</MsgId><Title><![CDATA[Link \"TEST\" Title]]></Title><Description><![CDATA[Link DESC]]></Description><Url><![CDATA[http://www.163.com/s/a/d/f/a]]></Url></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "打开链接会不会中毒？怕怕");
	}
	
	@Test
	public void imageMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129715</CreateTime>  <MsgId>17</MsgId>  <MsgType>image</MsgType>  <PicUrl>http://nos.netease.com/yixinpublic/pr_FzXvFRY8nrarFbQ9AphGAQ==_1379129714_6200108</PicUrl></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "<a href='http://nos.netease.com/yixinpublic/pr_FzXvFRY8nrarFbQ9AphGAQ==_1379129714_6200108'>下载图片</a>");
	}
	
	@Test
	public void locationMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129814</CreateTime>  <MsgId>18</MsgId>  <MsgType>location</MsgType>  <Location_X>30.302664</Location_X>  <Location_Y>120.159327</Location_Y>  <Scale>15</Scale></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "嗯哼？你的行踪已经被我知道了。");
	}
	@Test
	public void audioMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379086098</CreateTime>  <MsgId>29</MsgId>  <MsgType>audio</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_B7StF30nYDDT7VWrGzQxuw==_1379086096_6169298</url>  <name>600c4c87-146c-4d62-acb1-30d3f9ee3532.aac</name>  <mimeType>audio/aac</mimeType></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "嗯哼，你在说什么，我的听力还没有全部发育哟^");
	}
	
	@Test
	public void videoMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129699</CreateTime>  <MsgId>15</MsgId>  <MsgType>video</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_opNFMEeTepg0k2n3FbasyA==_1379129698_6206864</url>  <name>f4e5ce4254d188a590e31bbd0cb77fd5.mp4</name>  <mimeType>video/mp4</mimeType></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = acx.getBean(TextMsgParser.class).toMsg(result);
		assertEquals(msg.getContent(), "嘿嘿，我是近视眼，看不清楚呢。");
	}
}
