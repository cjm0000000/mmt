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
	 * process the message
	 * @param msg
	 * @return
	 */
	String processMsg(int cust_id, String msg);
	
	
}
