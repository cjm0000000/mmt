package com.github.cjm0000000.mmt.core.access;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;

/**
 * message gateway<br>
 * for message access
 * @author lemon
 * @version 2.0
 *
 */
public interface MsgGateWay {
	/**
	 * process message and generate a reply message
	 * @param cfg
	 * @param is
	 * @return
	 */
	BaseMessage processMsg(MmtConfig cfg, InputStream is);
}
