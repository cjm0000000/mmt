package lemon.shared.common;

/**
 * Message parser
 * @author lemon
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
