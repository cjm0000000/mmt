package lemon.weixin.biz;

import lemon.weixin.MessageFactory;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.util.WXHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thoughtworks.xstream.XStream;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MessageCloneTest {
	private XStream xStream = WXHelper.createXstream();
	@Test
	public void text(){
		TextMessage txt = MessageFactory.makeTextMsg();
		xStream.processAnnotations(TextMessage.class);
		txt = (TextMessage) xStream.fromXML(xStream.toXML(txt));
		TextMessage txt2 = (TextMessage) txt.cloneMsg();
		txt2 = (TextMessage) xStream.fromXML(xStream.toXML(txt2));
		
		System.out.println(txt);
		System.out.println(txt2);
		assertTrue("After clone, contents should equals.", txt.getContent().equals(txt2.getContent()));
	}
	
}
