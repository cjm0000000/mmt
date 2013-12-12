package com.github.cjm0000000.mmt.core.test.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.ServiceType;
import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.test.base.MmtTestBase;

/**
 * Test for MmtXMLParser
 * @author lemon
 * @version 2.0
 *
 */
public class MmtXMLParserTest extends MmtTestBase {

	/**
	 * The test for text message
	 */
	@Test
	public void testParserTextMsg(){
		ServiceType[] services = ServiceType.values();
		for (ServiceType serviceType : services) 
			parserTextMsg(serviceType);
	}
	
	/**
	 * parser text message to TextMessage
	 * @param service_type
	 */
	private void parserTextMsg(ServiceType service_type){
		TextMessage original = new TextMessage();
		original.setContent("Hello W 你好");
		original.setCreateTime((int) (System.currentTimeMillis()/1000));
		original.setCust_id(CUST_ID);
		original.setFromUserName("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		original.setId(0);
		original.setMsgId(5939685126051988739L);
		original.setService_type(service_type);
		original.setToUserName("gh_de370ad657cf");
		String msg = "<xml>"
				+ "<ToUserName><![CDATA["+original.getToUserName()+"]]></ToUserName>"
				+ "<FromUserName><![CDATA["+original.getFromUserName()+"]]></FromUserName>"
				+ "<CreateTime>"+original.getCreateTime()+"</CreateTime>"
				+ "<MsgType><![CDATA["+original.getMsgType()+"]]></MsgType>"
				+ "<Content><![CDATA[" + original.getContent() + "]]></Content>"
				+ "<MsgId>"+original.getMsgId()+"</MsgId>"
				+ "</xml>";
		System.out.println(msg);
		InputStream is = makeIS(msg);
		TextMessage after = (TextMessage) MmtXMLParser.fromXML(service_type, is);
		System.out.println(after);
		verifyBaseMsg(after, original);
		assertEquals(after.getContent(), original.getContent());
	}
	
	/**
	 * make a input stream from string
	 * @param msg
	 * @return
	 */
	private InputStream makeIS(String msg){
		try {
			return new ByteArrayInputStream(msg.getBytes(MmtCharset.LOCAL_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new MmtException("Unsupported Encoding.", e.getCause());
		}
	}
	
	/**
	 * verify basic message
	 * @param after the message after parser
	 * @param before the original message
	 */
	private void verifyBaseMsg(Message after, Message before){
		assertNotNull(after);
		assertNotNull(before);
		assertEquals(after.getCreateTime(), before.getCreateTime());
		assertEquals(after.getFromUserName(), before.getFromUserName());
		assertEquals(after.getMsgId(), before.getMsgId());
		assertEquals(after.getMsgType(), before.getMsgType());
		assertEquals(after.getId(), before.getId());
		assertEquals(after.getService_type(), before.getService_type());
		assertEquals(after.getToUserName(), before.getToUserName());
	}
}
