package lemon.shared.common;

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
	String parseMessage(String token, String msg);
	
}
