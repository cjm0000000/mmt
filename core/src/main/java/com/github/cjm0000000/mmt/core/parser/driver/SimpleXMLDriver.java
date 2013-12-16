package com.github.cjm0000000.mmt.core.parser.driver;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

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
	
	static{
		initDocumentBuilder();
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
		//TODO inject values
		
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
	private String getValue(Document doc, String itemName) {
		return doc.getElementsByTagName(itemName).item(0) == null ? null : doc
				.getElementsByTagName(itemName).item(0).getTextContent().trim();
	}
}
