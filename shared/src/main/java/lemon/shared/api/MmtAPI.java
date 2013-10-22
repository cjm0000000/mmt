package lemon.shared.api;

import lemon.shared.api.simple.MMTConfig;
import lemon.shared.log.bean.SiteAccess;
import lemon.shared.request.bean.ReturnCode;

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
	boolean verifySignature(SiteAccess sa);
	
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
}
