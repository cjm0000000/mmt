package lemon.weixin.dao;

import static org.junit.Assert.*;
import lemon.weixin.message.WeiXinMsgHelper;
import lemon.weixin.message.bean.EventMessage;
import lemon.weixin.message.bean.ImageMessage;
import lemon.weixin.message.bean.LinkMessage;
import lemon.weixin.message.bean.LocationMessage;
import lemon.weixin.message.bean.TextMessage;
import lemon.weixin.message.bean.VideoMessage;
import lemon.weixin.message.bean.VoiceMessage;
import lemon.weixin.message.parser.EventMsgParser;
import lemon.weixin.message.parser.ImageMsgParser;
import lemon.weixin.message.parser.LinkMsgParser;
import lemon.weixin.message.parser.LocationMsgParser;
import lemon.weixin.message.parser.TextMsgParser;
import lemon.weixin.message.parser.VideoMsgParser;
import lemon.weixin.message.parser.VoiceMsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RecvMsgDetailTest {
	private WeiXinMsgHelper msgHelper;
	private ApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgHelper = acx.getBean(WeiXinMsgHelper.class);
		assertNotNull(msgHelper);
	}
	@Test
	public void testSaveTextMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>";
		TextMessage msgObj = acx.getBean(TextMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvTextMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveImageMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027514</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/QXd6JDcZQ1ls9utpyRLS49qltXnkjkg3DOcQSI8CO1NxptcHC16yhQ/0]]></PicUrl><MsgId>5918583105618182187</MsgId><MediaId><![CDATA[7scBMzahwP7VG0exqbE4PwDhmu87f3jiYCdOueP0gpzghvrAugPxKHvMYxTLjQqX]]></MediaId></xml>";
		ImageMessage msgObj = acx.getBean(ImageMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvImageMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLocationMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027962</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>30.302176</Location_X><Location_Y>120.158966</Location_Y><Scale>20</Scale><Label><![CDATA[浙江省 杭州市: 310000]]></Label><MsgId>5918585029763530796</MsgId></xml>";
		LocationMessage msgObj = acx.getBean(LocationMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvLocationMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveEventMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[TEst Key]]></EventKey></xml>";
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
	public void testSaveVoiceMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378193706</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[fLHx02T1fxxHyN1j2C1xiDnjklwpEYb3EyvkxykCeQ1VAlqpvepM-l4jOIKYkIo4]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5919296894823039086</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		VoiceMessage msgObj = acx.getBean(VoiceMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVoiceMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 5919296894823039086L);
	}
	@Test
	public void testSaveVideoMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378194082</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[1BZrnnbpR-Es-kuOzWbWKCpuWonEy-5r7PrZd4lliGeqwumf-ik7obib7eiALxWc]]></MediaId><ThumbMediaId><![CDATA[DeuiUHn9EW8ETn10s1BCnDM8ScTuixsMMTjaNWtIKJzJPS6Xz92VXVGUREeu89yp]]></ThumbMediaId><MsgId>5919298509730742383</MsgId></xml>";
		VideoMessage msgObj = acx.getBean(VideoMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVideoMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 5919298509730742383L);
	}
	@Test
	public void testGetTextMsg(){
		TextMessage msg = msgHelper.getRecvTextMsg(148);
		System.out.println(msg);
	}

}
