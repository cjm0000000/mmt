package lemon.shared.api;

import lemon.shared.access.ReturnCode;
import lemon.shared.access.Access;
import lemon.shared.config.MMTConfig;

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
	 * @param sa
	 * @return if signature is valid return true, else return false.
	 */
	boolean verifySignature(Access sa);
	
	/**
	 * process message and return a result
	 * @param mmt_token MMT system token
	 * @param msg input message
	 * @return	reply message
	 */
	String processMsg(String mmt_token, String msg);
	
	/**
	 * Third-part get access token
	 * @param config
	 * @return access token
	 */
	String getAcessToken(MMTConfig config);
	
	/**
	 * Third part create menus
	 * @param config
	 * @param menuJson
	 * @return
	 */
	ReturnCode createMenus(MMTConfig config, String menuJson);
	
	
	/**
	 * Third part get menus from WeiXin server
	 * @param config
	 * @return
	 */
	String getMenus(MMTConfig config);
	
	/**
	 * Third part delete menus
	 * @param config
	 * @return
	 */
	ReturnCode deleteMenus(MMTConfig config);
	
	/**
	 * Upload media to API server
	 * @param config
	 * @param type
	 * @param file
	 * @param fileName
	 * @return
	 */
	String uploadMedia(MMTConfig config, String type, byte[] file,
			String fileName);
}
