package lemon.shared.api;

import java.util.Map;

/**
 * MMT message API
 * @author lemon
 * @version 1.0
 * @date 2013-08-21
 *
 */
public interface MmtAPI {
	/**
	 * verify the signature
	 * @param params
	 * @return if signature is valid return true, else return false.
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
	 * @param mmt_token
	 * @return access token
	 */
	String getAcessToken(String mmt_token);
	
	/**
	 * Third part create menus
	 * @param mmt_token
	 * @param menuJson
	 * @return
	 */
	String createMenus(String mmt_token, String menuJson);
	
	
	/**
	 * Third part get menus from WeiXin server
	 * @param mmt_token
	 * @return
	 */
	String getMenus(String mmt_token);
	
	/**
	 * Third part delete menus
	 * @param mmt_token
	 * @return
	 */
	String deleteMenus(String mmt_token);
}
