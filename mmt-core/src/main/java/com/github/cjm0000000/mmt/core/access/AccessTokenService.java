package com.github.cjm0000000.mmt.core.access;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceProperty;

/**
 * access token service
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface AccessTokenService extends ServiceProperty {

  /**
   * get access token
   * 
   * @param cfg
   * @return
   */
  String getAccessToken(MmtConfig cfg);
}
