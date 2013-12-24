package com.github.cjm0000000.mmt.core.message.processor;

import com.github.cjm0000000.mmt.core.service.MmtService;
import com.github.cjm0000000.mmt.core.service.ServiceProperty;

/**
 * customer message business interface
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
	String process(String mmt_token, MmtService msg);
}
