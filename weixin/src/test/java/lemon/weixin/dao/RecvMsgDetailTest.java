package lemon.weixin.dao;

import static org.junit.Assert.*;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.biz.parser.EventMsgParser;
import lemon.weixin.biz.parser.ImageMsgParser;
import lemon.weixin.biz.parser.LinkMsgParser;
import lemon.weixin.biz.parser.LocationMsgParser;
import lemon.weixin.biz.parser.TextMsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RecvMsgDetailTest {
	private WXRecvMsgDetailMapper msgMapper;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		msgMapper = (WXRecvMsgDetailMapper) acx.getBean(WXRecvMsgDetailMapper.class);
		assertNotNull(msgMapper);
	}
	@Test
	public void testSaveTextMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>";
		TextMessage msgObj = new TextMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveTextMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveImageMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027514</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/QXd6JDcZQ1ls9utpyRLS49qltXnkjkg3DOcQSI8CO1NxptcHC16yhQ/0]]></PicUrl><MsgId>5918583105618182187</MsgId><MediaId><![CDATA[7scBMzahwP7VG0exqbE4PwDhmu87f3jiYCdOueP0gpzghvrAugPxKHvMYxTLjQqX]]></MediaId></xml>";
		ImageMessage msgObj = new ImageMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveImageMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLocationMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378027962</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>30.302176</Location_X><Location_Y>120.158966</Location_Y><Scale>20</Scale><Label><![CDATA[浙江省 杭州市: 310000]]></Label><MsgId>5918585029763530796</MsgId></xml>";
		LocationMessage msgObj = new LocationMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveLocationMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveEventMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[TEst Key]]></EventKey></xml>";
		EventMessage msgObj = new EventMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveEventMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveLinktMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378030035</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url><![CDATA[weixin.qq.com]]></Url><MsgId>5918585029763530796</MsgId></xml>";
		LinkMessage msgObj = new LinkMsgParser().toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveLinkMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}

}
