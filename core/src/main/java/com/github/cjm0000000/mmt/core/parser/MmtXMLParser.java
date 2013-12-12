package com.github.cjm0000000.mmt.core.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.ServiceType;
import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * MMT XML parser
 * @author lemon
 * @version 2.0
 *
 */
public final class MmtXMLParser {
	private static final String ELEMENT_FOR_MESSAGE_TYPE = "MsgType";
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	/**
	 * Parser XML
	 * @param xml
	 * @return
	 */
	public static Message fromXML(ServiceType service_type, InputStream is){
		//get builder
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MmtException("Build XML parser faild.", e.getCause());
		}
		if(builder == null)
			return null;
		//parser to Document
		Document doc = null;
		try (Reader reader = new InputStreamReader(is, MmtCharset.LOCAL_CHARSET)){
			InputSource source = new InputSource(reader);
			doc = builder.parse(source);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null)
			return null;
		// parser document to Message
		String msgType = getValue(doc, ELEMENT_FOR_MESSAGE_TYPE);
		return newMessage(doc, service_type, msgType);
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
	 * 将document的值注入Message
	 * @param msg
	 * @param doc
	 */
	private static void injectValues(Message msg, Document doc){
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
					injectValue2Field(msg, field, getValue(doc, itemName));
				}
				superClass = superClass.getSuperclass();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new MmtException("Can't Convert XML to Message.", e.getCause());
		}
	}
	
	/**
	 * Inject value to field
	 * @param field
	 * @param value
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static void injectValue2Field(Message msg, Field field, String value) throws IllegalArgumentException, IllegalAccessException{
		if(field == null || value == null)
			return;
		field.setAccessible(true);
		Class<?> fieldType = field.getType();
		if (String.class.equals(fieldType)) {
			field.set(msg, value);
		} else if (int.class.equals(fieldType)) {
			field.setInt(msg, parserInt(value));
		} else if (long.class.equals(fieldType)) {
			field.setLong(msg, parserLong(value));
		} else if(Long.class.equals(fieldType)){
			field.setLong(msg, parserLong(value));
		}else if (float.class.equals(fieldType)) {
			field.setFloat(msg, parserFloat(value));
		} else if (double.class.equals(fieldType)) {
			field.setDouble(msg, parserDouble(value));
		}
	}
	
	/**
	 * 生成一个Message对象
	 * @param doc
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	private static Message newMessage(Document doc, ServiceType service_type,
			String msgType) {
		if (service_type == null || msgType == null)
			return null;
		Message msg;
		switch (msgType) {
		case MsgType.TEXT:
			msg = new TextMessage();
			break;
		default:
			msg = new Message();
			break;
		}
		injectValues(msg, doc);
		return msg;
	}
	
	/**
	 * Parser to int
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
	 * Parser to long
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
	 * Parser to float
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
	 * Parser to double
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
