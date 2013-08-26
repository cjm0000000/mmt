package lemon.shared.common;

/**
 * Message parser
 * @author lemon
 *
 */
public interface MsgParser {
	
	/**
	 * parse message and generate a replay message
	 * @param msg
	 * @return
	 */
	String parseMessage(String msg);
	
}
