package com.github.cjm0000000.mmt.core.test.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.core.test.MmtTestBase;

/**
 * XML Tester template
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractXMLParser extends MmtTestBase {
	private static final Logger logger = Logger.getLogger(AbstractXMLParser.class);
	/**
	 * make specific nodes
	 * @param sb
	 * @param original
	 */
	protected abstract void makeSpecNodes(StringBuilder sb, BaseMessage original);
	
	/**
	 * verify specific fields
	 * @param after
	 * @param before
	 */
	protected abstract void verifySpecFields(BaseMessage after, BaseMessage before);
	
	/**
	 * get message instance and set specific fields
	 * @return
	 */
	protected abstract BaseMessage getMsgInstance();
	
	/**
	 * parse from inputStream
	 * @param is
	 * @return
	 */
	protected abstract BaseMessage fromXML(InputStream is);
	
	/**
	 * Default Runner for the test suite
	 */
	@Test
	public void run(){
		ServiceType[] services = ServiceType.values();
		for (ServiceType serviceType : services) 
			parser(serviceType);
	}
	
	/**
	 * parser message
	 * @param service_type
	 */
	protected final void parser(ServiceType service_type){
		BaseMessage original = getMsgInstance();
		//set common values
		setCommonFields(service_type, original);
		//build XML string
		StringBuilder sb = new StringBuilder();
		makeCommonNodes(sb, original);
		makeSpecNodes(sb, original);
		sb.append("</xml>");
		logger.debug("generate XML string: " + sb.toString());
		//parser
		InputStream is = toInputStream(sb.toString());
		BaseMessage after = fromXML(is);
		//verify
		verifyCommonFields(after, original);
		verifySpecFields(after, original);
	}
	
	/**
	 * make a input stream from string
	 * @param msg
	 * @return
	 */
	private InputStream toInputStream(String msg){
		try {
			return new ByteArrayInputStream(msg.getBytes(MmtCharset.LOCAL_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new MmtException("Unsupported Encoding.", e.getCause());
		}
	}
	
	/**
	 * verify common fields
	 * @param after the message after parser
	 * @param before the original message
	 */
	private void verifyCommonFields(BaseMessage after, BaseMessage before){
		assertNotNull(after);
		assertNotNull(before);
		assertEquals(after.getCreateTime(), before.getCreateTime());
		assertEquals(after.getFromUserName(), before.getFromUserName());
		assertEquals(after.getMsgType(), before.getMsgType());
		assertEquals(after.getToUserName(), before.getToUserName());
	}
	
	/**
	 * set the message's fields with initialize value
	 * @param service_type
	 * @param original
	 */
	private void setCommonFields(ServiceType service_type, BaseMessage original){
		original.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
		original.setCust_id(CUST_ID);
		original.setFromUserName("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		original.setId(0);
		original.setService_type(service_type);
		original.setToUserName("gh_de370ad657cf");
	}
	
	/**
	 * make common nodes
	 * @param sb
	 * @param original
	 */
	private void makeCommonNodes(StringBuilder sb, BaseMessage original){
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+original.getToUserName()+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+original.getFromUserName()+"]]></FromUserName>");
		sb.append("<CreateTime>"+original.getCreateTime()+"</CreateTime>");
		sb.append("<MsgType><![CDATA["+original.getMsgType()+"]]></MsgType>");
	}
}
