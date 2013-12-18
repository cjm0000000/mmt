package com.github.cjm0000000.mmt.core.parser.driver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Simple XML driver implement
 * @author lemon
 * @version 2.0
 *
 */
public final class SimpleXMLDriver {
	private static final String ELEMENT_FOR_MESSAGE_TYPE = "MsgType";
	private static final Logger logger = Logger.getLogger(MmtXMLParser.class);
	private static DocumentBuilder builder;
	private final Itr classItr;
	
	public SimpleXMLDriver(boolean inject){
		classItr = inject ? new ItrForInject() : new ItrForParse();
	}
	
	static{
		initDocumentBuilder();
	}
	
	/**
	 * Iterate for class
	 *
	 */
	abstract static class Itr{
		/**
		 * do action such as inject values or parse field to XML
		 * @param msg
		 * @param field
		 * @param value
		 */
		abstract void doAction(SimpleMessageService msg, Field field, String value);
		
		/**
		 * Iterate class fields
		 * @param msg
		 * @param doc
		 */
		final void traverseClass(final SimpleMessageService msg, final Document doc){
			if(doc == null || msg == null)
				return;
			Class<?> superClass = msg.getClass();
			Field[] fields;
			try {
				while (!superClass.equals(BaseService.class)) {
					fields = superClass.getDeclaredFields();
					traverseFields(msg, doc, fields);
					superClass = superClass.getSuperclass();
				}
			} catch (IllegalArgumentException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
		}
		
		/**
		 * iterate for fields
		 * @param msg
		 * @param doc
		 * @param fields
		 */
		private void traverseFields(SimpleMessageService msg, Document doc,Field[] fields){
			MmtAlias alias;
			String itemName;
			for (Field field : fields) {
				alias = field.getAnnotation(MmtAlias.class);
				itemName = (alias == null) ? field.getName() : alias.value();
				doAction(msg, field, getValue(doc, itemName));
			}
		}
	}
	
	static class ItrForInject extends Itr{
		
		@Override
		void doAction(SimpleMessageService msg, Field field, String value) {
			if(field == null || value == null)
				return;
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			try {
				//most useful
				if(String.class.equals(fieldType)){
					field.set(msg, value);
					return;
				}
				//is primitive type
				if(fieldType.isPrimitive()){
					parsePrimitiveType(msg, field, value, fieldType);
					return;
				}
				//is boxed type
				if(!parseBoxedPrimitiveType(msg, field, fieldType, value)){
					if(fieldType.isEnum()){
						field.set(msg, getEnumValue(fieldType, value));
						return;
					}
					//TODO complex object
					processComplexObject(fieldType, value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
			if(logger.isDebugEnabled())
				logger.debug("inject value[" + value + "] to field[" + field.getName() + "]");
		}
		
		/**
		 * get enum value
		 * @param enumType
		 * @param value
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private Enum getEnumValue(Class<?> enumType, String value){
			return Enum.valueOf((Class<Enum>)enumType, value);
		}
		
		/**
		 * deal with complex object
		 */
		private void processComplexObject(Class<?> type, String value){
			
		}
		
		/**
		 * parse to primitive type
		 * @param fieldType
		 * @return
		 * @throws IllegalAccessException 
		 * @throws IllegalArgumentException 
		 */
		private void parsePrimitiveType(SimpleMessageService msg, Field field,
				String value, Class<?> fieldType)
				throws IllegalArgumentException, IllegalAccessException {
			field.set(msg, toPrimitiveValue(fieldType, value, getDefaultValue(fieldType)));
		}
		
		/**
		 * parse to boxed primitive type
		 * @param msg
		 * @param field
		 * @param fieldType
		 * @param value
		 * @return
		 * @throws IllegalAccessException 
		 * @throws IllegalArgumentException 
		 */
		private boolean parseBoxedPrimitiveType(SimpleMessageService msg,
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
		private <fieldType> Object toPrimitiveValue(Class<?> fieldType, String value,
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
		private Object getDefaultValue(Class<?> fieldType){
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
	
	/**
	 * class iterate for parse
	 *
	 */
	static class ItrForParse extends Itr{

		@Override
		void doAction(SimpleMessageService msg, Field field, String value) {
			//TODO implement class iterate for parse
		}
		
	}
	
	/**
	 * parse from XML
	 * @param is
	 * @param type
	 * @return
	 */
	public <T> SimpleMessageService fromXML(InputStream is, Class<T> type) {
		//parser to Document
		Document doc = null;
		try (InputStream inputStream = is){
			doc = builder.parse(inputStream);
			if(logger.isDebugEnabled())
				logger.debug("Parse document successfully!!!");
		} catch (SAXException | IOException e) {
			throw new MmtException("Can't parse xml to document.", e.getCause());
		}
		if(doc == null)
			return null;
		// parser document to Message
		String msgType = getValue(doc, ELEMENT_FOR_MESSAGE_TYPE);
		SimpleMessageService msg;
		try {
			msg = (SimpleMessageService) type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MmtException(
					"Can't new message instance: message type is " + type, e.getCause());
		}
		//filter null
		if(msg == null)
			return null;
		if(msg.getMsgType() == null || !msg.getMsgType().equals(msgType))
			throw new MmtException("message does' exists. message type is " + msgType);
		if(logger.isDebugEnabled())
			logger.debug("Message type is: " + msgType);
		classItr.traverseClass(msg, doc);
		return msg;
	}
	
	/**
	 * parse to XML
	 * @param msg
	 * @return
	 */
	public String toXML(Object msg){
		
		return null;
	}
	
	/**
	 * get value from document
	 * @param doc
	 * @param itemName
	 * @return
	 */
	private static String getValue(Document doc, String itemName) {
		return doc.getElementsByTagName(itemName).item(0) == null ? null : doc
				.getElementsByTagName(itemName).item(0).getTextContent().trim();
	}
	
	/**
	 * Initialize document builder
	 */
	private static void initDocumentBuilder(){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			if(logger.isDebugEnabled())
				logger.debug("DocumentBuilder initialize successfully!!!");
		} catch (ParserConfigurationException e) {
			throw new MmtException("Build XML parser faild.", e.getCause());
		}
	}
}
