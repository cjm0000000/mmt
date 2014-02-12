package com.github.cjm0000000.mmt.shared.fans.remote;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.fans.Fans;

/**
 * Fans APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface FansAPI {
  /**
   * get user information from remote server
   * 
   * @param cfg
   * @param openId
   * @return
   */
  Fans getUserInfo(MmtConfig cfg, String openId);

  /**
   * get fans list from remote server
   * 
   * @param cfg
   * @param next_openid
   * @return
   */
  JSONResponse getFans(MmtConfig cfg, String next_openid);
}
