package lemon.shared.api;

import java.util.Map;

/**
 * MMT message API
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
	 * process message and return a result
	 * @param mmt_token MMT system token
	 * @param msg input message
	 * @return	reply message
	 */
	String processMsg(String mmt_token, String msg);
	
	/**
	 * Third-part get access token
	 * @return access_token
	 */
	String getAcessToken();
}
