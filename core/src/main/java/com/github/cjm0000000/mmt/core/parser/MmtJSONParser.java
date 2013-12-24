package com.github.cjm0000000.mmt.core.parser;

import com.github.cjm0000000.mmt.core.parser.driver.SimpleJSONDriver;

/**
 * MMT JSON parser
 * @author lemon
 * @version 2.0
 *
 */
public class MmtJSONParser {
	private static final SimpleJSONDriver driver = new SimpleJSONDriver();
	
	/**
	 * parse from JSON
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> Object fromJSON(String json, Class<T> type){
		return driver.fromJSON(json, type);
	}
	
	/**
	 * generate JSON from java object
	 * @param obj
	 * @return
	 */
	public static String toJSON(Object obj){
		return driver.toJSON(obj);
	}
}
