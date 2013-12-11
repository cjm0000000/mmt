package com.github.cjm0000000.mmt.core.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.ServiceType;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;

/**
 * MMT XML parser
 * @author lemon
 * @version 2.0
 *
 */
public final class MmtXMLParser {
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
			throw new MmtException("构建XML解析器失败。", e.getCause());
		}
		if(builder == null)
			return null;
		//parser to Document
		Document doc = null;
		try {
			doc = builder.parse(is);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null)
			return null;
		// parser document to Message
		String msgType = doc.getElementsByTagName("MsgType").item(0).getNodeValue();
		Message msg = newMessage(service_type, msgType);
		return msg;
	}
	
	/**
	 * 生成一个Message对象
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	private static Message newMessage(ServiceType service_type, String msgType){
		if(service_type == null || msgType == null)
			return null;
		switch (msgType) {
		case MsgType.TEXT:
			return new TextMessage();

		default:
			return new Message();
		}
	}
}
