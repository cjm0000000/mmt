package lemon.shared.message.parser;

import lemon.shared.config.MMTConfig;

/**
 * Message parser
 * @author lemon
 * @version 1.0
 *
 */
public interface MsgParser {
	
	/**
	 * Parse message and generate a replay message
	 * @param msg
	 * @return
	 */
	String parseMessage(MMTConfig cfg, String msg);
	
}
