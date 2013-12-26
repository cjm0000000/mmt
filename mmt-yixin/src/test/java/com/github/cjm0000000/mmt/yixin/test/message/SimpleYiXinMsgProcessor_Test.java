package com.github.cjm0000000.mmt.yixin.test.message;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lemon.shared.api.MmtAPI;
import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.parser.TextMsgParser;
import lemon.yixin.message.processor.SimpleYiXinMsgProcessor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.customer.Customer;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomerRepository;
import com.github.cjm0000000.mmt.yixin.YiXin;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;
import com.github.cjm0000000.mmt.yixin.config.persistence.YiXinConfigRepository;
import com.github.cjm0000000.mmt.yixin.test.AbstractYiXinTester;

public class SimpleYiXinMsgProcessor_Test extends AbstractYiXinTester {
	@Autowired @Qualifier("yiXinAPI")
	private MmtAPI api;
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String Welcome_msg = "Welcome.";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = SimpleYiXinMsgProcessor.class.getName();
	private final int CUST_ID = -5746;
	@Autowired
	private MsgManager msgHelper;
	@Autowired
	private CustomerRepository customerMapper;
	@Autowired
	private YiXinConfigRepository	yxConfigMapper;
	@Autowired @Qualifier("MMT_text")
	private TextMsgParser textMsgParser;
	
	@Before
	public void init() {
		//add customer
		Customer cust = customerMapper.getCustomer(CUST_ID);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(CUST_ID);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus(Status.AVAILABLE);
			customerMapper.addCustomer(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		//add YiXin configure
		YiXinConfig cfg = yxConfigMapper.get(CUST_ID);
		if(null == cfg){
			cfg = new YiXinConfig();
			cfg.setCust_id(CUST_ID);
			cfg.setToken(TOKEN);
			cfg.setApi_url(MMT_TOKEN);
			cfg.setYx_account("lemon_test");
			cfg.setAppid("");
			cfg.setSecret("");
			cfg.setBiz_class(bizClass);
			cfg.setSubscribe_msg(Subscribe_msg);
			cfg.setWelcome_msg(Welcome_msg);
			yxConfigMapper.save(cfg);
			assertNotEquals(cfg.getCust_id(), 0);
		}
		YiXin.init();
		YiXin.setConfig(cfg);
	}
	@Test
	public void testSaveTextMsg(){
		String txtMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379080088</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>你好</Content></xml>";
		TextMessage msg = textMsgParser.toMsg(txtMsg);
		msg.setService_type(ServiceType.YIXIN);
		msgHelper.saveRecvTextMsg(msg);
	}
	@Test
	public void parserMsgType() throws JDOMException, IOException{
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379128652</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>你好</Content></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes("UTF-8"));
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		Assert.assertTrue("text".equals(msgType.getValue()));
	}
	@Test
	public void textMsgTest(){
		String txtMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379128652</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>你好</Content></xml>";
		String result = api.processMsg(MMT_TOKEN, txtMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "Welcome.");
	}
	@Test
	public void subscribeTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379085091</CreateTime>  <MsgId>21</MsgId>  <MsgType>event</MsgType>  <Event>subscribe</Event>  <EventKey></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), Subscribe_msg);
	}
	@Test
	public void unsubscribe(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379083706</CreateTime>  <MsgId>12</MsgId>  <MsgType>event</MsgType>  <Event>unsubscribe</Event>  <EventKey></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		assertEquals(result, null);
	}
	@Test
	public void linkMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1379129715</CreateTime><MsgType><![CDATA[link]]></MsgType><MsgId>1024102410241024</MsgId><Title><![CDATA[Link \"TEST\" Title]]></Title><Description><![CDATA[Link DESC]]></Description><Url><![CDATA[http://www.163.com/s/a/d/f/a]]></Url></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "亲，我暂时无法识别链接信息哦，您可以给我发文字消息。");
	}
	@Test
	public void imageMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129715</CreateTime>  <MsgId>17</MsgId>  <MsgType>image</MsgType>  <PicUrl>http://nos.netease.com/yixinpublic/pr_FzXvFRY8nrarFbQ9AphGAQ==_1379129714_6200108</PicUrl></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "亲，我暂时无法识别图片信息哦，您可以给我发文字消息。");
	}
	@Test
	public void locationMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129814</CreateTime>  <MsgId>18</MsgId>  <MsgType>location</MsgType>  <Location_X>30.302664</Location_X>  <Location_Y>120.159327</Location_Y>  <Scale>15</Scale></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "亲，我暂时无法识别地理位置信息哦，您可以给我发文字消息。");
	}
	
	@Test
	public void videoMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129699</CreateTime>  <MsgId>15</MsgId>  <MsgType>video</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_opNFMEeTepg0k2n3FbasyA==_1379129698_6206864</url>  <name>f4e5ce4254d188a590e31bbd0cb77fd5.mp4</name>  <mimeType>video/mp4</mimeType></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(),"亲，我暂时无法识别视频信息哦，您可以给我发文字消息。");
	}
	@Test
	public void audioMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379086098</CreateTime>  <MsgId>29</MsgId>  <MsgType>audio</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_B7StF30nYDDT7VWrGzQxuw==_1379086096_6169298</url>  <name>600c4c87-146c-4d62-acb1-30d3f9ee3532.aac</name>  <mimeType>audio/aac</mimeType></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(),"亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}
	
	@Test
	public void musicMsgTest(){
		String recvMsg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129915</CreateTime>  <MsgId>20</MsgId>  <MsgType>music</MsgType>  <url>http://p3.music.126.net/h5PuQCIHeLkrqK0x33xAHg==/76965813957105.jpg</url>  <name>36220</name>  <mimeType>audio/mpeg</mimeType>  <desc>{&quot;id&quot;:365865,&quot;artists&quot;:&quot;My Little Airport&quot;,&quot;picUrl&quot;:&quot;http://p3.music.126.net/h5PuQCIHeLkrqK0x33xAHg==/76965813957105.jpg&quot;,&quot;albumId&quot;:36220,&quot;audio&quot;:&quot;http://m1.music.126.net/8xbwk09vVjKZdfxVxDMv0A==/2094569650923372.mp3&quot;,&quot;album&quot;:&quot;在动物园散步才是正经事&quot;,&quot;name&quot;:&quot;王菲，关于你的眉&quot;}</desc></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(),"亲，我暂时无法识别音乐哦，您可以给我发文字消息。");
	}
	@Override
	protected void defaultCase() {
		
	}
	
}
