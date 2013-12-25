package lemon.shared.message.parser;

import com.github.cjm0000000.mmt.core.config.MmtConfig;

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
	String parseMessage(MmtConfig cfg, String msg);
	
}
