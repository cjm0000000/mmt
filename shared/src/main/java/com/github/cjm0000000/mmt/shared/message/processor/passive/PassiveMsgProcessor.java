package com.github.cjm0000000.mmt.shared.message.processor.passive;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.processor.MsgProcessor;
import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * passive message processor
 * @author lemon
 * @version 2.0
 *
 */
public abstract class PassiveMsgProcessor implements MsgProcessor {
	private static final Logger logger = Logger.getLogger(PassiveMsgProcessor.class);
	
	/**
	 * get MMT configure
	 * @param mmt_token
	 * @return
	 */
	public abstract MmtConfig getConfig(String mmt_token);
	
	@Override
	public String process(String mmt_token, MmtService recvMsg) {
		MmtConfig cfg = getConfig(mmt_token);
		if(cfg == null)
			throw new MmtException("MMT config is null.");
		if(logger.isDebugEnabled())
			logger.debug("MMT configure is " + cfg);
		//TODO process message
		return null;
	}

}
