package com.github.cjm0000000.mmt.core.parser;


import java.io.InputStream;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.parser.driver.SimpleXMLDriver;

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
	 * @param type
	 * @return
	 */
	public static <T> BaseMessage fromXML(InputStream is, Class<T> type) {
		return simpleDriver.fromXML(is, type);
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
