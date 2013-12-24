package lemon.shared.message.processor;

import com.github.cjm0000000.mmt.core.service.ServiceProperty;

import lemon.shared.message.metadata.Message;

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
