package lemon.shared.message.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.thoughtworks.xstream.XStream;

import lemon.shared.MMTContext;
import lemon.shared.MmtException;
import lemon.shared.config.MMTCharset;
import lemon.shared.config.MMTConfig;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.parser.MsgParser;
import lemon.shared.message.processor.MsgProcessor;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.xstream.XStreamHelper;

/**
 * A message parser for WeiXin
 * @author lemon
 * @version 1.0
 *
 */
public abstract class AbstractMsgParser implements MsgParser {
	public static final String PREFIX = "MMT_";
	private static Log logger = LogFactory.getLog(AbstractMsgParser.class);
	protected XStream xStream = XStreamHelper.createXMLXStream();
	
	/**
	 * Get message parser
	 * @param service_type
	 * @param msg
	 * @return
	 */
	public static MsgParser getParser(ServiceType service_type, String msg){
		String msgType = getMsgType(msg);
		try{
			return (MsgParser) MMTContext.getApplicationContext().getBean(PREFIX + msgType);
		}catch(NoSuchBeanDefinitionException e){
			return (MsgParser) MMTContext.getApplicationContext().getBean(service_type + "_" + msgType);
		}
	}
	
	/**
	 * Generate replay message
	 * @param mmt_token
	 * @param msg
	 * @return
	 */
	public final String parseMessage(MMTConfig cfg, String msg){
		Message message = toMsg(msg);
		// process business
		String retMsg = process(cfg, message);
		// build replay message
		return retMsg;
	}
	
	/**
	 * Convert string to message 
	 * @param msg
	 * @return
	 */
	protected abstract Message toMsg(String msg);
	
	/**
	 * Convert WeiXin message to XML
	 * @param rMsg
	 * @return
	 */
	protected abstract String toXML(Message rMsg);
	
	
	/**
	 * Business process
	 * @param mmt_token
	 * @param msg
	 * @return an string message
	 */
	private String process(MMTConfig cfg, Message msg){
		if(null == cfg)
			throw new MmtException("No customer's configure find.");
		MsgProcessor processor = null;
		try {
			processor = (MsgProcessor) MMTContext.getApplicationContext().getBean(Class.forName(cfg.getBiz_class()));
			return processor.processMsg(cfg.getApi_url(), msg);
		} catch (ClassNotFoundException e) {
			logger.error("Can't find business implement class: " + cfg.getBiz_class());
			throw new MmtException("Can't find business implement class: " + cfg.getBiz_class());
		}
	}
	
	/**
	 * Get message type
	 * @param msg
	 * @return
	 */
	private static String getMsgType(String msg) {
		try(InputStream is = new ByteArrayInputStream(msg.getBytes(MMTCharset.LOCAL_CHARSET))) {
			Document doc = new SAXBuilder().build(is);
			Element e = doc.getRootElement().getChild("MsgType");
			return e.getValue();
		} catch (IOException | JDOMException e) {
			logger.error("Can't get message type:" + e.getMessage());
		}
		return null;
	}
}
