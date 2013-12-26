package lemon.shared.api;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.access.Access;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;

import lemon.shared.message.metadata.Message;

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
	String getAcessToken(MmtConfig config);
	
	/**
	 * Third part create menus
	 * @param config
	 * @param menuJson
	 * @return
	 */
	ReturnCode createMenus(MmtConfig config, String menuJson);
	
	
	/**
	 * Third part get menus from WeiXin server
	 * @param config
	 * @return
	 */
	String getMenus(MmtConfig config);
	
	/**
	 * Third part delete menus
	 * @param config
	 * @return
	 */
	ReturnCode deleteMenus(MmtConfig config);
	
	/**
	 * Upload media to API server
	 * @param config
	 * @param type
	 * @param file
	 * @param fileName
	 * @return
	 */
	String uploadMedia(MmtConfig config, String type, byte[] file,
			String fileName);
	
	/**
	 * Send message(active)
	 * @param config
	 * @param msg
	 * @return
	 */
	ReturnCode sendMsg(MmtConfig config, Message msg);
}
