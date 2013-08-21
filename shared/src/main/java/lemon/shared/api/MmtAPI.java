package lemon.shared.api;

import java.util.Map;

/**
 * unified operation for mmt
 * @author lemon
 * @date 2013-08-21
 *
 */
public interface MmtAPI {
	/**
	 * verify the signature
	 * @param params
	 * @return
	 */
	boolean verifySignature(Map<String, Object> params);
	
	/**
	 * generate replay message
	 * @param msg
	 * @return
	 */
	String getReplayMsg(String msg);
	
	
}
