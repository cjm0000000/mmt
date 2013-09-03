package lemon.weixin.dao;

import static org.junit.Assert.*;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.biz.WeiXinMsgHelper;
import lemon.weixin.biz.parser.EventMsgParser;
import lemon.weixin.biz.parser.ImageMsgParser;
import lemon.weixin.biz.parser.LinkMsgParser;
import lemon.weixin.biz.parser.LocationMsgParser;
import lemon.weixin.biz.parser.TextMsgParser;
import lemon.weixin.biz.parser.VideoMsgParser;
import lemon.weixin.biz.parser.VoiceMsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RecvMsgDetailTest {
	private WeiXinMsgHelper msgHelper;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		msgHelper = acx.getBean(WeiXinMsgHelper.class);
		assertNotNull(msgHelper);
	}
	@Test
	public void testSaveTextMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>";
		TextMessage msgObj = new TextMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvTextMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveImageMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027514</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/QXd6JDcZQ1ls9utpyRLS49qltXnkjkg3DOcQSI8CO1NxptcHC16yhQ/0]]></PicUrl><MsgId>5918583105618182187</MsgId><MediaId><![CDATA[7scBMzahwP7VG0exqbE4PwDhmu87f3jiYCdOueP0gpzghvrAugPxKHvMYxTLjQqX]]></MediaId></xml>";
		ImageMessage msgObj = new ImageMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvImageMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLocationMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027962</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>30.302176</Location_X><Location_Y>120.158966</Location_Y><Scale>20</Scale><Label><![CDATA[浙江省 杭州市: 310000]]></Label><MsgId>5918585029763530796</MsgId></xml>";
		LocationMessage msgObj = new LocationMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvLocationMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveEventMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[TEst Key]]></EventKey></xml>";
		EventMessage msgObj = new EventMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvEventMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLinktMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url><![CDATA[weixin.qq.com]]></Url><MsgId>5918585029763530796</MsgId></xml>";
		LinkMessage msgObj = new LinkMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvLinkMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	@Test
	public void testSaveVoiceMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378193706</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[fLHx02T1fxxHyN1j2C1xiDnjklwpEYb3EyvkxykCeQ1VAlqpvepM-l4jOIKYkIo4]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5919296894823039086</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		VoiceMessage msgObj = new VoiceMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVoiceMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 5919296894823039086L);
	}
	@Test
	public void testSaveVideoMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378194082</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[1BZrnnbpR-Es-kuOzWbWKCpuWonEy-5r7PrZd4lliGeqwumf-ik7obib7eiALxWc]]></MediaId><ThumbMediaId><![CDATA[DeuiUHn9EW8ETn10s1BCnDM8ScTuixsMMTjaNWtIKJzJPS6Xz92VXVGUREeu89yp]]></ThumbMediaId><MsgId>5919298509730742383</MsgId></xml>";
		VideoMessage msgObj = new VideoMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgHelper.saveRecvVideoMsg(msgObj);
		assertNotEquals(msgObj.getId(),0);
		assertEquals(msgObj.getMsgId().longValue(), 5919298509730742383L);
	}

}
