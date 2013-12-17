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
		 * do action such as inject values or parse field to xml
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
			MmtAlias alias;
			String itemName;
			try {
				while (!superClass.equals(BaseService.class)) {
					fields = superClass.getDeclaredFields();
					for (Field field : fields) {
						alias = field.getAnnotation(MmtAlias.class);
						if (alias != null)
							itemName = alias.value();
						else
							itemName = field.getName();
						doAction(msg, field, getValue(doc, itemName));
					}
					superClass = superClass.getSuperclass();
				}
			} catch (IllegalArgumentException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
		}
	}
	
	static class ItrForInject extends Itr{
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		void doAction(SimpleMessageService msg, Field field, String value) {
			if(field == null || value == null)
				return;
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			try {
				if (String.class.equals(fieldType)) {
					field.set(msg, value);
				} else if (int.class.equals(fieldType)) {
					field.setInt(msg, parserInt(value));
				} else if (long.class.equals(fieldType)) {
					field.setLong(msg, parserLong(value));
				} else if(Long.class.equals(fieldType)){
					field.set(msg, new Long(parserLong(value)));
				}else if (float.class.equals(fieldType)) {
					field.setFloat(msg, parserFloat(value));
				} else if (double.class.equals(fieldType)) {
					field.setDouble(msg, parserDouble(value));
				}else{
					if(fieldType.isEnum()){
						field.set(msg, Enum.valueOf((Class<Enum>)fieldType, value));
					}else
						field.set(msg, value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
			if(logger.isDebugEnabled())
				logger.debug("inject value[" + value + "] to field[" + field.getName() + "]");
		}
		
		/**
		 * Parse to int
		 * @param v
		 * @return
		 */
		private static int parserInt(String v) {
			if (v == null || "".equals(v.trim()))
				return 0;
			try {
				return Integer.parseInt(v);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		
		/**
		 * Parse to long
		 * @param v
		 * @return
		 */
		private static long parserLong(String v){
			if (v == null || "".equals(v.trim()))
				return 0;
			try {
				return Long.parseLong(v);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		
		/**
		 * Parse to float
		 * @param v
		 * @return
		 */
		private static float parserFloat(String v){
			if (v == null || "".equals(v.trim()))
				return 0.0F;
			try {
				return Float.parseFloat(v);
			} catch (NumberFormatException e) {
				return 0.0F;
			}
		}
		
		/**
		 * Parse to double
		 * @param v
		 * @return
		 */
		private static double parserDouble(String v){
			if (v == null || "".equals(v.trim()))
				return 0.0D;
			try {
				return Double.parseDouble(v);
			} catch (NumberFormatException e) {
				return 0.0D;
			}
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
			logDebug("Parse document successfully!!!");
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
		logDebug("Message type is: " + msgType);
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
	 * print debug log
	 * @param log
	 */
	private void logDebug(Object log){
		if(logger.isDebugEnabled())
			logger.debug(log);
	}
}
