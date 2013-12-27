package com.github.cjm0000000.mmt.shared.customer;

import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;

/**
 * custom menu operation
 * @author lemon
 * @version 2.0
 *
 */
public interface CustomMenuAPI extends InitiativeProcessor {
	/**
     * create menus on remote server
     * @param menuJson
     * @return
     */
    ReturnCode createMenus(String menuJson);
    
    
    /**
     * get menus from remote server
     * @return
     */
    String getMenus();
    
    /**
     * delete menus from remote server
     * @return
     */
    ReturnCode deleteMenus();
}
