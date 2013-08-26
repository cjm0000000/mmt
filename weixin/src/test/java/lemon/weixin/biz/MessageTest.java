package lemon.weixin.biz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MessageTest {
	@Test
	public void parserMsgType() throws JDOMException, IOException{
		String msg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377241649729</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[hello,weixin, I am lemon.]]></Content></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		System.out.println(msgType.getValue());
		Assert.assertTrue("text".equals(msgType.getValue()));
	}
}
