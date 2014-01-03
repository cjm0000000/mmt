package com.github.cjm0000000.mmt.core.message.process;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceProperty;

/**
 * customer initiative message processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface InitiativeProcessor extends ServiceProperty {

  /**
   * get access token
   * 
   * @param cfg
   * @return
   */
  String getAccessToken(MmtConfig cfg);
}
