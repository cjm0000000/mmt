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

import static com.github.cjm0000000.mmt.core.tookit.convert.PrimitiveTypeConvert.*;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtOmitField;

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
	private static InternalParserForInject ipfi;
	private static ItrForParse ifp;
	private static final String TAG_NAME = "#NODE#";
	private static final String START_TAG = "<" + TAG_NAME + ">";
	private static final String END_TAG = "</" + TAG_NAME + ">";
	/** CDATA prefix */
	private static final String PREFIX_CDATA = "<![CDATA[";
	/** CDATA suffix */
	private static final String SUFFIX_CDATA = "]]>";
	
	static class InternalParserForInject{
		
		/**
		 * data transfer
		 * @param clzObj	类结构对象
		 * @param dataObj	存放数据的对象
		 */
		final void traverseClass(final Object clzObj, final Document doc){
			if(doc == null || clzObj == null)
				return;
			Class<?> superClass = clzObj.getClass();
			Field[] fields;
			try {
				while (!superClass.equals(BaseService.class) && !Object.class.equals(superClass)) {
					fields = superClass.getDeclaredFields();
					traverseFields(clzObj, doc, fields);
					superClass = superClass.getSuperclass();
				}
			} catch (IllegalArgumentException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
		}
		
		/**
		 * inject value to field
		 * @param obj
		 * @param field
		 * @param value
		 */
		private void doInject(Object obj, Field field, String value) {
			if(!(obj instanceof SimpleMessageService))
				return;
			if(field == null || value == null)
				return;
			SimpleMessageService msg = (SimpleMessageService) obj;
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
		 * iterate for fields
		 * @param clzObj
		 * @param dataObj
		 * @param fields
		 */
		private void traverseFields(Object clzObj, Document dataObj, Field[] fields) {
			MmtAlias alias;
			String itemName;
			for (Field field : fields) {
				alias = field.getAnnotation(MmtAlias.class);
				itemName = (alias == null) ? field.getName() : alias.value();
				doInject(clzObj, field, getValue(dataObj, itemName));
			}
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
		private void parsePrimitiveType(SimpleMessageService msg, Field field, String value, Class<?> fieldType)
				throws IllegalArgumentException, IllegalAccessException {
			field.set(msg, toPrimitiveValue(fieldType, value, getDefaultValue(fieldType)));
		}
		
	}
	
	/**
	 * class iterate for parse
	 *
	 */
	static class ItrForParse {
		
		/**
		 * data transfer
		 * @param clzObj
		 * @param sb
		 * @param endTags
		 */
		final void traverseClass(final Object dataObj, final StringBuilder sb) {
			if(sb == null || dataObj == null)
				return;
			Class<?> superClass = dataObj.getClass();
			//add class start tag
			MmtAlias alias = superClass.getAnnotation(MmtAlias.class);
			String tagName = alias == null ? superClass.getName() : alias.value();
			sb.append(getStartTag(tagName));
			//process fields
			Field[] fields;
			try {
				while (!Object.class.equals(superClass)) {
					fields = superClass.getDeclaredFields();
					traverseFields(dataObj, fields, sb);
					superClass = superClass.getSuperclass();
				}
			} catch (IllegalArgumentException e) {
				throw new MmtException("Can't Convert XML to Message.", e.getCause());
			}
			//add class end tag
			sb.append(getEndTag(tagName));
		}
		
		/**
		 * iterate for fields
		 * @param fields
		 * @param sb
		 * @throws IllegalAccessException 
		 * @throws IllegalArgumentException 
		 */
		private void traverseFields(Object data, Field[] fields, StringBuilder sb){
			MmtAlias alias;
			String itemName;
			try {
				for (Field field : fields) {
					if(field.getAnnotation(MmtOmitField.class) != null)
						continue;
					alias = field.getAnnotation(MmtAlias.class);
					itemName = (alias == null) ? field.getName() : alias.value();
					field.setAccessible(true);
					processLeaf(itemName, field.get(data), sb, field.getAnnotation(MmtCDATA.class) != null);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MmtException(e.getMessage(), e.getCause());
			}
		}

		/**
		 * process leaf node
		 * @param tagName
		 * @param value
		 * @param sb
		 * @param needCDATA
		 */
		private void processLeaf(String tagName, Object value,
				StringBuilder sb, boolean needCDATA) {
			if (value == null)
				return;
			sb.append(getStartTag(tagName));
			sb.append(needCDATA ? toCDATA(value) : value);
			sb.append(getEndTag(tagName));
		}
		
		/**
		 * get start tag
		 * @param nodeName
		 * @return
		 */
		private String getStartTag(String nodeName){
			return START_TAG.replaceAll(TAG_NAME, nodeName);
		}
		
		/**
		 * get end tag
		 * @param nodeName
		 * @return
		 */
		private String getEndTag(String nodeName){
			return END_TAG.replaceAll(TAG_NAME, nodeName);
		}
		
		/**
		 * to CDATA string
		 * @param value
		 * @return
		 */
		private String toCDATA(Object value){
			return PREFIX_CDATA + value + SUFFIX_CDATA;
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
			if(builder == null)
				initDocumentBuilder();
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
		if (ipfi == null)
			ipfi = new InternalParserForInject();
		ipfi.traverseClass(msg, doc);
		return msg;
	}
	
	/**
	 * parse to XML
	 * @param obj
	 * @return
	 */
	public String toXML(Object obj){
		if(obj == null)
			return null;
		if(ifp == null)
			ifp = new ItrForParse();
		StringBuilder sb = new StringBuilder();
		ifp.traverseClass(obj, sb);
		return sb.toString();
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
	private void initDocumentBuilder(){
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
