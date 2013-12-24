package com.github.cjm0000000.mmt.core.parser.driver;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.github.cjm0000000.mmt.core.parser.filter.SendMsgPropFilter;

/**
 * Simple JSON driver implement
 * @author lemon
 * @version 2.0
 *
 */
public class SimpleJSONDriver {
	private static final Logger logger = Logger.getLogger(SimpleJSONDriver.class);
	
	public String toJSON(Object obj){
		if(logger.isDebugEnabled())
			logger.debug("begin to generate JSON.");
		return JSON.toJSONString(obj,
				SendMsgPropFilter.getInstance());
	}
	
	public <T> Object fromJSON(String json, Class<T> type){
		if(logger.isDebugEnabled())
			logger.debug("begin to parse JSON.");
		return JSON.parseObject(json, type);
	}
	
}
