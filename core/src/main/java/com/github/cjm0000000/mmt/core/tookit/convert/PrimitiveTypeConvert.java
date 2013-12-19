package com.github.cjm0000000.mmt.core.tookit.convert;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.SimpleMessageService;

/**
 * convert of primitive types
 * @author lemon
 * @version 2.0
 *
 */
public class PrimitiveTypeConvert {
	private static final Logger logger = Logger.getLogger(PrimitiveTypeConvert.class);
	
	/**
	 * parse to boxed primitive type<br>
	 * if parse successful, return true, else return false
	 * @param msg
	 * @param field
	 * @param fieldType
	 * @param value
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean parseBoxedPrimitiveType(SimpleMessageService msg,
			Field field, Class<?> fieldType, String value)
			throws IllegalArgumentException, IllegalAccessException {
		if(Integer.class.equals(fieldType)){
			field.setInt(msg, new Integer((int) toPrimitiveValue(int.class, value, 0)));
			return true;
		}
		if(Short.class.equals(fieldType)){
			field.setShort(msg, new Short((short) toPrimitiveValue(short.class, value, 0b0)));
			return true;
		}
		if(Long.class.equals(fieldType)){
			field.setLong(msg, new Long((long) toPrimitiveValue(long.class, value, 0)));
			return true;
		}
		if(Byte.class.equals(fieldType)){
			field.setByte(msg, new Byte((byte) toPrimitiveValue(byte.class, value, 0b0)));
			return true;
		}
		if(Float.class.equals(fieldType)){
			field.setFloat(msg, new Float((float) toPrimitiveValue(float.class, value, 0.0F)));
			return true;
		}
		if(Double.class.equals(fieldType)){
			field.setDouble(msg, (double) toPrimitiveValue(double.class, value, 0.0D));
			return true;
		}
		if(Boolean.class.equals(fieldType)){
			field.setBoolean(msg, new Boolean((boolean) toPrimitiveValue(boolean.class, value, false)));
			return true;
		}
		return false;
	}
	
	/**
	 * parse to primitive type
	 * @param fieldType
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static <fieldType> Object toPrimitiveValue(Class<?> fieldType, String value,
			Object defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		try{
			if(int.class.equals(fieldType))		return Integer.parseInt(value);
			if(short.class.equals(fieldType)) 	return Short.parseShort(value);
			if(long.class.equals(fieldType))	return Long.parseLong(value);
			if(byte.class.equals(fieldType))	return Byte.parseByte(value);
			if(float.class.equals(fieldType)) 	return Float.parseFloat(value);
			if(double.class.equals(fieldType)) 	return Double.parseDouble(value);
			if(boolean.class.equals(fieldType)) return Boolean.parseBoolean(value);
		}catch(NumberFormatException e){
			if(logger.isDebugEnabled())
				logger.debug("Can't convert to primitive type, return default value " + defaultValue);
		}
		return defaultValue == null ? value : defaultValue;
	}
	
	/**
	 * get default value
	 * @param fieldType
	 * @return
	 */
	public static Object getDefaultValue(Class<?> fieldType){
		if(int.class.equals(fieldType))	return 0;
		if(short.class.equals(fieldType)) return 0;
		if(long.class.equals(fieldType))return 0L;
		if(byte.class.equals(fieldType))return 0b0;
		if(float.class.equals(fieldType)) return 0.0F;
		if(double.class.equals(fieldType)) return 0.0D;
		if(boolean.class.equals(fieldType)) return false;
		return "";
	}
}
