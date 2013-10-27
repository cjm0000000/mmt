package lemon.shared.message.processor;

import lemon.shared.message.metadata.Message;
import lemon.shared.service.ServiceProperty;

/**
 * WeiXin customer message business interface
 * @author lemon
 * @version 1.0
 *
 */
public interface MsgProcessor extends ServiceProperty {
	/**
	 * customer message process
	 * @param mmt_token	is unique in MMT system
	 * @param msg
	 * @return
	 */
	String processMsg(String mmt_token, Message msg);
	
}
