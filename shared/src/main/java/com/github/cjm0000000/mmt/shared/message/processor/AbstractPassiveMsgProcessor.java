package com.github.cjm0000000.mmt.shared.message.processor;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.processor.PassiveMsgProcessor;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * passive message processor
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractPassiveMsgProcessor implements PassiveMsgProcessor {
	private static final Logger logger = Logger.getLogger(AbstractPassiveMsgProcessor.class);
	
	/**
	 * get MMT configure
	 * @param mmt_token
	 * @return
	 */
	protected abstract MmtConfig getConfig(String mmt_token);
	
	protected abstract BaseMessage processMessage(SimpleRecvMessage msg);
	
	protected abstract BaseMessage processEvent(SimpleEvent event);
	
	@Override
	public final BaseMessage process(String mmt_token, MmtService recvMsg) {
		MmtConfig cfg = getConfig(mmt_token);
		if(cfg == null)
			throw new MmtException("MMT config is null.");
		if(logger.isDebugEnabled())
			logger.debug("MMT configure is " + cfg);
		recvMsg.setCust_id(cfg.getCust_id());
		recvMsg.setService_type(getServiceType());
		if(recvMsg instanceof SimpleRecvMessage){
			// process passive message
			return processMessage((SimpleRecvMessage) recvMsg);
		}
		if(recvMsg instanceof SimpleEvent){
			// process passive event
			return null;
		}
		throw new MmtException("No such message instance.", new ClassCastException());
	}

}
