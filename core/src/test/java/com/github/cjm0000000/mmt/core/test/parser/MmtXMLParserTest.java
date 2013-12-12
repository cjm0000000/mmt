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
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
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
	 * The unit test cases for text message
	 */
	@Test
	public void runTest() {
		ServiceType[] services = ServiceType.values();
		for (ServiceType serviceType : services) {
			parserTextMsg(serviceType);
			parserImageMsg(serviceType);
			parserVoiceMsg(serviceType);
		}
	}
	
	/**
	 * parser text message to TextMessage
	 * @param service_type
	 */
	private void parserTextMsg(ServiceType service_type){
		TextMessage original = new TextMessage();
		original.setContent("Hello W 你好");
		setMessageField(service_type, original);
		StringBuilder sb = new StringBuilder();
		setXMLField(sb, original);
		sb.append("<Content><![CDATA[" + original.getContent() + "]]></Content>");
		sb.append("</xml>");
		InputStream is = makeIS(sb.toString());
		TextMessage after = (TextMessage) MmtXMLParser.fromXML(service_type, is);
		verifyBaseMsg(after, original);
		assertEquals(after.getContent(), original.getContent());
	}
	
	/**
	 * parser image message to ImageMessage
	 * @param service_type
	 */
	private void parserImageMsg(ServiceType service_type){
		ImageMessage original = new ImageMessage();
		setMessageField(service_type, original);
		original.setPicUrl("http://mmbiz.qpic.cn/mmbiz/QXd6JDcZQ1kNscXWUKkI4ZuLcZQQZtPIicAOB2ic5iaXKzxWytwobOXQKjiaGYFO9aO2wCGJWLyuuyhicaUqictyOibNQ/0");
		if(ServiceType.WEIXIN.equals(service_type))
			original.setMediaId("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		StringBuilder sb = new StringBuilder();
		setXMLField(sb, original);
		sb.append("<PicUrl><![CDATA["+original.getPicUrl()+"]]></PicUrl>");
		sb.append("<MediaId><![CDATA["+original.getMediaId()+"]]></MediaId>");
		sb.append("</xml>");
		InputStream is = makeIS(sb.toString());
		ImageMessage after = (ImageMessage) MmtXMLParser.fromXML(service_type, is);
		verifyBaseMsg(after, original);
		assertEquals(after.getPicUrl(), original.getPicUrl());
		if(ServiceType.WEIXIN.equals(service_type))
			assertEquals(after.getMediaId(), original.getMediaId());
	}
	
	/**
	 * parser voice message to VoiceMessage
	 * @param service_type
	 */
	private void parserVoiceMsg(ServiceType service_type){
		VoiceMessage original = new VoiceMessage();
		setMessageField(service_type, original);
		original.setFormat("amr");
		original.setMediaId("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		StringBuilder sb = new StringBuilder();
		setXMLField(sb, original);
		sb.append("<Format><![CDATA["+original.getFormat()+"]]></Format>");
		sb.append("<MediaId><![CDATA["+original.getMediaId()+"]]></MediaId>");
		sb.append("</xml>");
		InputStream is = makeIS(sb.toString());
		VoiceMessage after = (VoiceMessage) MmtXMLParser.fromXML(service_type, is);
		verifyBaseMsg(after, original);
		assertEquals(after.getMediaId(), original.getMediaId());
		assertEquals(after.getFormat(), original.getFormat());
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
		assertEquals(after.getToUserName(), before.getToUserName());
	}
	
	/**
	 * set the message's fields with initialize value
	 * @param service_type
	 * @param original
	 */
	private void setMessageField(ServiceType service_type, Message original){
		original.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
		original.setCust_id(CUST_ID);
		original.setFromUserName("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		original.setId(0);
		original.setMsgId(5939685126051988740L);
		original.setService_type(service_type);
		original.setToUserName("gh_de370ad657cf");
	}
	
	/**
	 * generate basic XML fields
	 * @param sb
	 * @param original
	 */
	private void setXMLField(StringBuilder sb, Message original){
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+original.getToUserName()+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+original.getFromUserName()+"]]></FromUserName>");
		sb.append("<CreateTime>"+original.getCreateTime()+"</CreateTime>");
		sb.append("<MsgType><![CDATA["+original.getMsgType()+"]]></MsgType>");
		sb.append("<MsgId>"+original.getMsgId()+"</MsgId>");
	}
}
