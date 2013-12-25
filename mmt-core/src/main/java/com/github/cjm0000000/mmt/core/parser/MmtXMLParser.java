package com.github.cjm0000000.mmt.core.parser;


import java.io.InputStream;

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
		return simpleDriver.fromXML(is, null);
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
