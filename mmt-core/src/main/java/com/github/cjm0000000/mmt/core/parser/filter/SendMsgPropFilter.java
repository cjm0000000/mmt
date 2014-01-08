package com.github.cjm0000000.mmt.core.parser.filter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.github.cjm0000000.mmt.core.MmtBase;

/**
 * send message properties filter
 * @author lemon
 * @version 2.0
 *
 */
public class SendMsgPropFilter implements PropertyPreFilter {
	private final Set<String> excludes;
	private final static SendMsgPropFilter instance = new SendMsgPropFilter();
	
	private SendMsgPropFilter(){
		Class<?> clazz = MmtBase.class;
		Field[] excludeFields = clazz.getDeclaredFields();
		excludes = new HashSet<String>(excludeFields.length);
		for (Field field : excludeFields)
			excludes.add(field.getName());
	}
	
	public static SendMsgPropFilter getInstance(){
		return instance;
	}
	
	@Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
		if (source == null) 
            return true;
        if (excludes.contains(name))
            return false;
		return true;
	}

}
