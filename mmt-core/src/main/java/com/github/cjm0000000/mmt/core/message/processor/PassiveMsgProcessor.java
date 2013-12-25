package com.github.cjm0000000.mmt.core.message.processor;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.service.MmtService;
import com.github.cjm0000000.mmt.core.service.ServiceProperty;

/**
 * customer passive message processor
 * @author lemon
 * @version 2.0
 *
 */
public interface PassiveMsgProcessor extends ServiceProperty {
	/**
	 * process passive message 
	 * @param mmt_token	 unique in MMT system
	 * @param msg
	 * @return
	 */
	BaseMessage process(String mmt_token, MmtService msg);
}
