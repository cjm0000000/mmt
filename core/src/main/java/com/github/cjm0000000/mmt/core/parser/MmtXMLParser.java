package com.github.cjm0000000.mmt.core.parser;

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
import com.github.cjm0000000.mmt.core.ServiceType;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
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
	private static final Logger logger = Logger.getLogger(MmtXMLParser.class);
	private static DocumentBuilder builder;
	
	static{
		initDocumentBuilder();
	}
	/**
	 * Parser XML to SimpleMessageService
	 * @param xml
	 * @return
	 */
	public static SimpleMessageService fromXML(ServiceType service_type,
			InputStream is) {
		if(builder == null)
			initDocumentBuilder();
		//parser to Document
		Document doc = null;
		try (InputStream inputStream = is){
			doc = builder.parse(inputStream);
			if(logger.isDebugEnabled())
				logger.debug("Parse document successfully!!!");
		} catch (SAXException | IOException e) {
			throw new MmtException("Can't parse xml to document." ,e.getCause());
		}
		if(doc == null)
			return null;
		// parser document to Message
		String msgType = getValue(doc, ELEMENT_FOR_MESSAGE_TYPE);
		if(logger.isDebugEnabled())
			logger.debug("Message type is: " + msgType);
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
	 * Initialize document builder
	 */
	private static void initDocumentBuilder(){
		try {
			builder = factory.newDocumentBuilder();
			if(logger.isDebugEnabled())
				logger.debug("DocumentBuilder initialize successfully!!!");
		} catch (ParserConfigurationException e) {
			throw new MmtException("Build XML parser faild.", e.getCause());
		}
	}
	
	/**
	 * Inject values to Message
	 * @param msg
	 * @param doc
	 */
	private static void injectValues(SimpleMessageService msg, Document doc) {
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
	private static void injectValue2Field(SimpleMessageService msg,
			Field field, String value) throws IllegalArgumentException,
			IllegalAccessException {
		if(logger.isDebugEnabled())
			logger.debug("try inject value[" + value + "] to field[" + field.getName() + "]");
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
			field.set(msg, new Long(parserLong(value)));
		}else if (float.class.equals(fieldType)) {
			field.setFloat(msg, parserFloat(value));
		} else if (double.class.equals(fieldType)) {
			field.setDouble(msg, parserDouble(value));
		}
	}
	
	/**
	 * generate a SimpleMessageService object
	 * @param doc
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	private static SimpleMessageService newMessage(Document doc,
			ServiceType service_type, String msgType) {
		if (service_type == null || msgType == null)
			return null;
		SimpleMessageService msg;
		switch (msgType) {
		case MsgType.TEXT:
			msg = new TextMessage();
			break;
		case MsgType.IMAGE:
			msg = new ImageMessage();
			break;
		case MsgType.VOICE:
			msg = new VoiceMessage();
			break;
		case MsgType.AUDIO:
			msg = new AudioMessage();
			break;
		case MsgType.VIDEO:
			if(ServiceType.WEIXIN.equals(service_type))
				msg = new VideoMessage();
			else if(ServiceType.YIXIN.equals(service_type))
				msg = new Message();//TODO 易信VideoMessage fromXML
			else msg = new Message();
			break;
		case MsgType.LOCATION:
			msg = new LocationMessage();
			break;
		case MsgType.LINK:
			msg = new LinkMessage();
			break;
		case MsgType.EVENT:
			msg = new SimpleEvent();
			break;
		default:
			msg = new Message();
			break;
		}
		injectValues(msg, doc);
		return msg;
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
