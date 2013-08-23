package lemon.weixin.biz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lemon.weixin.bean.message.BasicMessage;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;

@RunWith(JUnit4.class)
public class MessageTest {
	@Test
	public void parserMsg() throws JDOMException, IOException{
		String msg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377241649729</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[hello,weixin, I am lemon.]]></Content></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		System.out.println(msgType.getValue());
		XStream xStream = new XStream(new JDomDriver());
		xStream.processAnnotations(BasicMessage.class);
		
		Object obj = xStream.fromXML(msg);
		System.out.println(obj);
	}
}
