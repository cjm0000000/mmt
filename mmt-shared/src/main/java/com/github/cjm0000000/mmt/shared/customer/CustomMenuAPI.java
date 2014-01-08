package com.github.cjm0000000.mmt.shared.customer;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;

/**
 * custom menu operation
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface CustomMenuAPI {
  /**
   * create menus on remote server
   * 
   * @param cfg
   * @param menuJson
   * @return
   */
  ReturnCode createMenus(MmtConfig cfg, String menuJson);

  /**
   * get menus from remote server
   * 
   * @param cfg
   * @return
   */
  String getMenus(MmtConfig cfg);

  /**
   * delete menus from remote server
   * 
   * @param cfg
   * @return
   */
  ReturnCode deleteMenus(MmtConfig cfg);
}
