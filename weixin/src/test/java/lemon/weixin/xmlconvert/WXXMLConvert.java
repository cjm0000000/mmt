package lemon.weixin.xmlconvert;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lemon.weixin.MessageFactory;
import lemon.weixin.bean.message.Article;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.EventType;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.util.WXHelper;

import com.thoughtworks.xstream.XStream;

import static org.junit.Assert.*;

/**
 * Test XML and Object convert via xstream
 * 
 * @author lemon
 * 
 */
@RunWith(JUnit4.class)
public class WXXMLConvert {
	private XStream xStream;

	public WXXMLConvert() {
		xStream = WXHelper.createXstream();
	}

	@Test
	public void testTextMessage() {
		xStream.processAnnotations(TextMessage.class);
		TextMessage txt = MessageFactory.makeTextMsg();
		String msg = xStream.toXML(txt);
		System.out.println(msg);
		System.out.println(txt.getContent());
		TextMessage txt2 = (TextMessage) xStream.fromXML(msg);
		System.out.println(txt2.getContent());
		assertTrue("Text message convert succesfully.",
				"hello,weixin, I am \"lemon\".".equals(txt2.getContent()));
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
		System.out.println(msg);
		System.out.println(img.getPicUrl());
		ImageMessage img2 = (ImageMessage) xStream.fromXML(msg);
		System.out.println(img2.getPicUrl());
		assertTrue("Image message convert succesfully.",
				"http://www.baidu.com/sadsaf".equals(img2.getPicUrl()));
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
		msg.setLabel("I am here.<xml>\"\"</xml>");
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		System.out.println(str);
		System.out.println(msg.getLabel());
		LocationMessage msg2 = (LocationMessage) xStream.fromXML(str);
		System.out.println(msg2.getLabel());

		assertTrue("Location X message convert faild.",
				msg.getLocation_X() == msg2.getLocation_X());
		assertTrue("Location Y message convert faild.",
				msg.getLocation_Y() == msg2.getLocation_Y());
		assertTrue("Location Label message convert faild.",
				"I am here.<xml>\"\"</xml>".equals(msg2.getLabel()));
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
		System.out.println(str);
		System.out.println(msg.getTitle());
		LinkMessage msg2 = (LinkMessage) xStream.fromXML(str);
		System.out.println(msg2.getTitle());
		
		assertTrue("Link Title message convert faild.",
				"Link \"TEST\" Title".equals(msg2.getTitle()));
		assertTrue("Link DESC message convert faild.",
				"Link DESC".equals(msg2.getDescription()));
		assertTrue("Link URL message convert faild.",
				"http://www.163.com/s/a/d/f/a".equals(msg2.getUrl()));
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
		System.out.println(str);
		System.out.println(msg.getEventType());
		EventMessage msg2 = (EventMessage) xStream.fromXML(str);
		System.out.println(msg2.getEventType());
		
		assertTrue("EVENT TYPE message convert faild.",
				EventType.SUBSCRIBE.equals(msg2.getEventType()));
		assertTrue("EVENT KEYS message convert faild.",
				"0dfsafkqwnriksdk".equals(msg2.getEventKey()));
	}
	
	@Test
	public void testMusicMessage() {
		xStream.processAnnotations(MusicMessage.class);
		MusicMessage msg = new MusicMessage();
		msg.setToUserName("weixin");
		msg.setFromUserName("lemon");
		msg.setCreateTime(new Date().getTime());
		msg.setMusicUrl("http://music.baidu.com/a/a/d.mp3");
		msg.setHqMusicUrl("HQmusic  ss s");
		msg.setMsgId(1024102410241024L);
		String str = xStream.toXML(msg);
		System.out.println(str);
		System.out.println(msg.getMusicUrl());
		MusicMessage msg2 = (MusicMessage) xStream.fromXML(str);
		System.out.println(msg2.getMusicUrl());
		
		assertTrue("MUSIC URL message convert faild.",
				"http://music.baidu.com/a/a/d.mp3".equals(msg2.getMusicUrl()));
		assertTrue("MUSIC HqMusicUrl message convert faild.",
				"HQmusic  ss s".equals(msg2.getHqMusicUrl()));
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
		System.out.println(str);
		System.out.println(msg.getArticleCount());
		NewsMessage msg2 = (NewsMessage) xStream.fromXML(str);
		System.out.println(msg2.getArticleCount());
		
		assertTrue("News ArticleCount message convert faild.",
				msg.getArticleCount() == msg2.getArticleCount());
	}

}
