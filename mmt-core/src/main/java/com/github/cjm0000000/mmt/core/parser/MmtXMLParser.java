package com.github.cjm0000000.mmt.core.parser;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.parser.driver.SimpleXMLDriver;
import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * MMT XML parser
 * @author lemon
 * @version 2.0
 *
 */
public final class MmtXMLParser {
	private static final SimpleXMLDriver simpleDriver = new SimpleXMLDriver();
	
	/**
	 * parse XML to SimpleMessageService
	 * @param is
	 * @param service_type
	 * @return
	 */
	public static BaseMessage fromXML(InputStream is, ServiceType service_type) {
		return simpleDriver.fromXML(is, service_type);
	}
	
	/**
	 * parse XML to SimpleMessageService
	 * @param is
	 * @return
	 */
	public static BaseMessage fromXML(InputStream is) {
		return fromXML(is, null);
	}
	
	/**
	 * parse XML to SimpleMessageService
	 * @param xml
	 * @param service_type
	 * @return
	 */
	public static BaseMessage fromXML(String xml, ServiceType service_type) {
		try {
			return fromXML(new ByteArrayInputStream(
							xml.getBytes(MmtCharset.LOCAL_CHARSET)), service_type);
		} catch (UnsupportedEncodingException e) {
			throw new MmtException("Unsupported Encoding:" + MmtCharset.LOCAL_CHARSET);
		}
	}
	
	/**
	 * parse XML to SimpleMessageService
	 * @param xml
	 * @param service_type
	 * @return
	 */
	public static BaseMessage fromXML(String xml) {
		return fromXML(xml, null);
	}
	
	/**
	 * parse SimpleMessageService to XML string
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj){
		return simpleDriver.toXML(obj);
	}
	
}
