package lemon.yixin.test.xmlconvert;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lemon.shared.xstream.XStreamHelper;
import lemon.yixin.bean.message.*;

import com.thoughtworks.xstream.XStream;

import static org.junit.Assert.*;

/**
 * Test XML and Object convert via xstream
 * 
 * @author lemon
 * 
 */
@RunWith(JUnit4.class)
public class YXXMLConvert {
	private static Log logger = LogFactory.getLog(YXXMLConvert.class);
	private XStream xStream;
	
	@Before
	public void init() {
		xStream = XStreamHelper.createXMLXStream();
	}

	@Test
	public void testTextMessage() {
		xStream.processAnnotations(TextMessage.class);
		TextMessage txt = new TextMessage();
		txt.setToUserName("weixin");
		txt.setFromUserName("lemon");
		txt.setCreateTime(new Date().getTime());
		txt.setContent("hello,weixin, I am \"lemon\".");
		txt.setMsgId(1024102410241024L);
		String msg = xStream.toXML(txt);
		TextMessage txt2 = (TextMessage) xStream.fromXML(msg);

		assertEquals(txt.getContent(), txt2.getContent());
		assertEquals(txt.getToUserName(), txt2.getToUserName());
	}

	@Test
	public void testImageMessage() {
		xStream.processAnnotations(ImageMessage.class);
		ImageMessage img = new ImageMessage();
		img.setToUserName("weixin");
		img.setFromUserName("lemon");
		img.setCreateTime(new Date().getTime());
		img.setPicUrl("http://www.baidu.com/sadsaf");
		img.setMsgId(1024102410241024L);
		String msg = xStream.toXML(img);
		ImageMessage img2 = (ImageMessage) xStream.fromXML(msg);
		
		assertEquals(img.getPicUrl(), img2.getPicUrl());
	}
	
	@Test
	public void testLocationMessage() {
		xStream.processAnnotations(LocationMessage.class);
		LocationMessage msg = new LocationMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setLocation_X(23.134521);
		msg.setLocation_Y(113.358803);
		msg.setScale(20);
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		LocationMessage msg2 = (LocationMessage) xStream.fromXML(str);

		assertTrue(msg.getLocation_X()==msg2.getLocation_X());
		assertTrue(msg.getLocation_Y()==msg2.getLocation_Y());
	}
	
	@Test
	public void testLinkMessage() {
		xStream.processAnnotations(LinkMessage.class);
		LinkMessage msg = new LinkMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setTitle("Link \"TEST\" Title");
		msg.setDescription("Link DESC");
		msg.setUrl("http://www.163.com/s/a/d/f/a");
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		LinkMessage msg2 = (LinkMessage) xStream.fromXML(str);
		
		assertEquals(msg.getTitle(), msg2.getTitle());
		assertEquals(msg.getDescription(), msg2.getDescription());
		assertEquals(msg.getUrl(), msg2.getUrl());
	}
	
	@Test
	public void testEventMessage() {
		xStream.processAnnotations(EventMessage.class);
		EventMessage msg = new EventMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setEventType(EventType.SUBSCRIBE);
		msg.setEventKey("0dfsafkqwnriksdk");
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		EventMessage msg2 = (EventMessage) xStream.fromXML(str);
		
		assertEquals(msg.getEventKey(), msg2.getEventKey());
		assertEquals(msg.getEventType(), msg2.getEventType());
	}
	
	@Test
	public void testMusicMessage() {
		xStream.processAnnotations(MusicMessage.class);
		MusicMessage msg = new MusicMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setMsgId(1024102410241024L);
		msg.setUrl("http://www.baidu.com");
		msg.setName("Music");
		msg.setMimeType("audio/aac");
		msg.setDesc("descdesc");
		String str = xStream.toXML(msg);
		logger.debug(str);
		MusicMessage msg2 = (MusicMessage) xStream.fromXML(str);
		
		assertEquals(msg.getUrl(), msg2.getUrl());
		assertEquals(msg.getName(), msg2.getName());
		assertEquals(msg.getMimeType(), msg2.getMimeType());
		assertEquals(msg.getDesc(), msg2.getDesc());
	}
	
	@Test
	public void testNewsMessage() {
		xStream.processAnnotations(NewsMessage.class);
		NewsMessage msg = new NewsMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setArticleCount(2);
		
		Article a1 = new Article();
		a1.setTitle("Title A1");
		a1.setDescription("DESC A1");
		a1.setPicUrl("pic.taobao.com/aaas/asdf.jpg");
		a1.setUrl("http://www.baidu.com");
		
		Article a2 = new Article();
		a2.setTitle("Title A2");
		a2.setDescription("DESC A2");
		a2.setPicUrl("pic2.taobao.com/aaas/asdf222.jpg");
		a2.setUrl("http://www.yousas.com");
		
		Article[] articles = {a1,a2};
		
		msg.setArticles(articles);
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		NewsMessage msg2 = (NewsMessage) xStream.fromXML(str);
		
		assertEquals(msg.getArticleCount(), msg2.getArticleCount());
	}

}
