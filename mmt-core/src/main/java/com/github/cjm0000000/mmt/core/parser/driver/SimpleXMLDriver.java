package com.github.cjm0000000.mmt.core.parser.driver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import static com.github.cjm0000000.mmt.core.tookit.convert.PrimitiveTypeConvert.*;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtOmitField;
import com.github.cjm0000000.mmt.core.service.MmtService;
import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * Simple XML driver implement
 * @author lemon
 * @version 2.0
 *
 */
public final class SimpleXMLDriver {
	private static final String ELEMENT_FOR_MESSAGE_TYPE = "MsgType";
	private static final String ELEMENT_FOR_EVENT_TYPE 	= "Event";
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
	
	private static final Map<String, Class<?>> recvTypes = new HashMap<>(64);
	
	static{
		// message
		Class<?> text 		= com.github.cjm0000000.mmt.core.message.recv.TextMessage.class;
		Class<?> image 		= com.github.cjm0000000.mmt.core.message.recv.ImageMessage.class;
		Class<?> link 		= com.github.cjm0000000.mmt.core.message.recv.LinkMessage.class;
		Class<?> location 	= com.github.cjm0000000.mmt.core.message.recv.LocationMessage.class;
		// event
		Class<?> sEvent		= com.github.cjm0000000.mmt.core.message.event.SimpleEvent.class;
		Class<?> scan		= com.github.cjm0000000.mmt.core.message.event.ScanEvent.class;
		Class<?> localEvent = com.github.cjm0000000.mmt.core.message.event.LocationEvent.class;
		Class<?> click		= com.github.cjm0000000.mmt.core.message.event.KeyEvent.class;
		//for WeiXin
		recvTypes.put(ServiceType.WEIXIN + MsgType.TEXT, text);
		recvTypes.put(ServiceType.WEIXIN + MsgType.IMAGE, image);
		recvTypes.put(ServiceType.WEIXIN + MsgType.LINK, link);
		recvTypes.put(ServiceType.WEIXIN + MsgType.LOCATION, location);
		recvTypes.put(ServiceType.WEIXIN + MsgType.VIDEO, com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage.class);
		recvTypes.put(ServiceType.WEIXIN + MsgType.VOICE, com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage.class);
		
		recvTypes.put(ServiceType.WEIXIN + MsgType.EVENT + EventType.subscribe, sEvent);
		recvTypes.put(ServiceType.WEIXIN + MsgType.EVENT + EventType.unsubscribe, sEvent);
		recvTypes.put(ServiceType.WEIXIN + MsgType.EVENT + EventType.scan, scan);
		recvTypes.put(ServiceType.WEIXIN + MsgType.EVENT + EventType.LOCATION, localEvent);
		recvTypes.put(ServiceType.WEIXIN + MsgType.EVENT + EventType.CLICK, click);
		//for YiXin
		recvTypes.put(ServiceType.YIXIN + MsgType.TEXT, text);
		recvTypes.put(ServiceType.YIXIN + MsgType.IMAGE, image);
		recvTypes.put(ServiceType.YIXIN + MsgType.LINK, link);
		recvTypes.put(ServiceType.YIXIN + MsgType.LOCATION, location);
		recvTypes.put(ServiceType.YIXIN + MsgType.AUDIO, com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage.class);
		
		recvTypes.put(ServiceType.YIXIN + MsgType.EVENT + EventType.subscribe, sEvent);
		recvTypes.put(ServiceType.YIXIN + MsgType.EVENT + EventType.unsubscribe, sEvent);
		recvTypes.put(ServiceType.YIXIN + MsgType.EVENT + EventType.scan, scan);
		recvTypes.put(ServiceType.YIXIN + MsgType.EVENT + EventType.LOCATION, localEvent);
		recvTypes.put(ServiceType.YIXIN + MsgType.EVENT + EventType.CLICK, click);
		
		//default
		recvTypes.put(MsgType.TEXT, text);
		recvTypes.put(MsgType.IMAGE, image);
		recvTypes.put(MsgType.LINK, link);
		recvTypes.put(MsgType.LOCATION, location);
		
		recvTypes.put(MsgType.EVENT + EventType.subscribe, sEvent);
		recvTypes.put(MsgType.EVENT + EventType.unsubscribe, sEvent);
		recvTypes.put(MsgType.EVENT + EventType.scan, scan);
		recvTypes.put(MsgType.EVENT + EventType.LOCATION, localEvent);
		recvTypes.put(MsgType.EVENT + EventType.CLICK, click);
	}
	
	static class InternalParserForInject {
		
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
				while (!superClass.equals(MmtService.class) && !Object.class.equals(superClass)) {
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
			if(!(obj instanceof BaseMessage))
				return;
			if(field == null || value == null)
				return;
			BaseMessage msg = (BaseMessage) obj;
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
		private void parsePrimitiveType(BaseMessage msg, Field field, String value, Class<?> fieldType)
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
		 * @param dataObj
		 * @param sb
		 * @param dealWithClass
		 */
		final void traverseClass(final Object dataObj, final StringBuilder sb, final boolean dealWithClass) {
			if(sb == null || dataObj == null)
				return;
			Class<?> superClass = dataObj.getClass();
			
			//add class start tag
			String tagName = null;
			if(dealWithClass){
				MmtAlias alias = superClass.getAnnotation(MmtAlias.class);
				tagName = alias == null ? superClass.getName() : alias.value();
				sb.append(getStartTag(tagName));
			}
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
			if(dealWithClass)
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
			//Judge value type
			if(needParse(value)){
				if(value.getClass().isArray()){
					int len = Array.getLength(value);
					for (int i = 0; i < len; i++) 
						traverseClass(Array.get(value, i), sb, true);
				}else
					traverseClass(value, sb, false);
			}else
				sb.append(needCDATA ? toCDATA(value) : value);
			sb.append(getEndTag(tagName));
		}
		
		/**
		 * Judge if need parse again
		 * @param value
		 * @return
		 */
		private boolean needParse(Object value){
			if(value == null)
				return  false;
			Class<?> targetClass = value.getClass();
			if(String.class.equals(targetClass))
				return false;
			if(targetClass.isEnum() || targetClass.isPrimitive())
				return false;
			if(value instanceof Number)
				return false;
			if(Boolean.class.equals(targetClass))
				return false;
			if(Object.class.equals(targetClass))
				return false;
			return true;
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
	 * @param service_type
	 * @return
	 */
	public BaseMessage fromXML(InputStream is, ServiceType service_type) {
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
		BaseMessage msg;
		try {
			if(MsgType.EVENT.equals(msgType)){
				String eventType = getValue(doc, ELEMENT_FOR_EVENT_TYPE);
				msg = (BaseMessage) getEventClassType(EventType.valueOf(eventType), service_type).newInstance();
			}else
				msg = (BaseMessage) getMsgClassType(msgType, service_type).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MmtException(
					"Can't new message instance.", e.getCause());
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
		ifp.traverseClass(obj, sb, true);
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
	 * get event class type
	 * @param eventType
	 * @param service_type
	 * @return
	 */
	private Class<?> getEventClassType(EventType eventType, ServiceType service_type){
		return service_type == null ? recvTypes.get(MsgType.EVENT + eventType)
				: recvTypes.get(service_type + MsgType.EVENT + eventType);
	}
	
	/**
	 * get message class type
	 * @param msgType
	 * @param service_type
	 * @return
	 */
	private Class<?> getMsgClassType(String msgType, ServiceType service_type) {
		return service_type == null ? recvTypes.get(msgType) : recvTypes
				.get(service_type + msgType);
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
