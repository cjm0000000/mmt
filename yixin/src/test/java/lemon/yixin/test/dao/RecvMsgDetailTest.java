package lemon.yixin.test.dao;

import static org.junit.Assert.*;
import lemon.yixin.message.YiXinMsgHelper;
import lemon.yixin.message.bean.AudioMessage;
import lemon.yixin.message.bean.EventMessage;
import lemon.yixin.message.bean.ImageMessage;
import lemon.yixin.message.bean.LinkMessage;
import lemon.yixin.message.bean.LocationMessage;
import lemon.yixin.message.bean.TextMessage;
import lemon.yixin.message.bean.VideoMessage;
import lemon.yixin.message.parser.AudioMsgParser;
import lemon.yixin.message.parser.EventMsgParser;
import lemon.yixin.message.parser.ImageMsgParser;
import lemon.yixin.message.parser.LinkMsgParser;
import lemon.yixin.message.parser.LocationMsgParser;
import lemon.yixin.message.parser.TextMsgParser;
import lemon.yixin.message.parser.VideoMsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RecvMsgDetailTest {
	private YiXinMsgHelper msgHelper;
	private ApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgHelper = acx.getBean(YiXinMsgHelper.class);
		assertNotNull(msgHelper);
	}
	@Test
	public void testSaveTextMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379080088</CreateTime>  <MsgId>5</MsgId>  <MsgType>text</MsgType>  <Content>你好</Content></xml>";
		TextMessage msgObj = acx.getBean(TextMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvTextMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveImageMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129715</CreateTime>  <MsgId>17</MsgId>  <MsgType>image</MsgType>  <PicUrl>http://nos.netease.com/yixinpublic/pr_FzXvFRY8nrarFbQ9AphGAQ==_1379129714_6200108</PicUrl></xml>";
		ImageMessage msgObj = acx.getBean(ImageMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvImageMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLocationMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129814</CreateTime>  <MsgId>18</MsgId>  <MsgType>location</MsgType>  <Location_X>30.302664</Location_X>  <Location_Y>120.159327</Location_Y>  <Scale>15</Scale></xml>";
		LocationMessage msgObj = acx.getBean(LocationMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvLocationMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveEventMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379085091</CreateTime>  <MsgId>21</MsgId>  <MsgType>event</MsgType>  <Event>subscribe</Event>  <EventKey></EventKey></xml>";
		EventMessage msgObj = acx.getBean(EventMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvEventMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLinktMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url><![CDATA[weixin.qq.com]]></Url><MsgId>5918585029763530796</MsgId></xml>";
		LinkMessage msgObj = acx.getBean(LinkMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvLinkMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	@Test
	public void testSaveAudioMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379086098</CreateTime>  <MsgId>29</MsgId>  <MsgType>audio</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_B7StF30nYDDT7VWrGzQxuw==_1379086096_6169298</url>  <name>600c4c87-146c-4d62-acb1-30d3f9ee3532.aac</name>  <mimeType>audio/aac</mimeType></xml>";
		AudioMessage msgObj = acx.getBean(AudioMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVoiceMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 29);
	}
	@Test
	public void testSaveVideoMsg(){
		String msg = "<xml>  <ToUserName>11b09b69e7e169ed</ToUserName>  <FromUserName>eddc9f8ab0c0afc9</FromUserName>  <CreateTime>1379129699</CreateTime>  <MsgId>15</MsgId>  <MsgType>video</MsgType>  <url>http://nos.netease.com/yixinpublic/pr_opNFMEeTepg0k2n3FbasyA==_1379129698_6206864</url>  <name>f4e5ce4254d188a590e31bbd0cb77fd5.mp4</name>  <mimeType>video/mp4</mimeType></xml>";
		VideoMessage msgObj = acx.getBean(VideoMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVideoMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 15);
	}
	@Test
	public void testGetTextMsg(){
		TextMessage msg = msgHelper.getRecvTextMsg(148);
		System.out.println(msg);
	}

}
