package com.github.cjm0000000.mmt.core.parser;


import java.io.InputStream;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.driver.SimpleXMLDriver;

/**
 * MMT XML parser
 * @author lemon
 * @version 2.0
 *
 */
public final class MmtXMLParser {
	private static final SimpleXMLDriver injectDriver = new SimpleXMLDriver(true);
	private static final SimpleXMLDriver parseDriver = new SimpleXMLDriver(false);
	
	/**
	 * parse XML to SimpleMessageService
	 * @param is
	 * @param type
	 * @return
	 */
	public static <T> SimpleMessageService fromXML(InputStream is, Class<T> type) {
		return injectDriver.fromXML(is, type);
	}
	
	/**
	 * parse SimpleMessageService to XML string
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj){
		return parseDriver.toXML(obj);
	}
	
}
