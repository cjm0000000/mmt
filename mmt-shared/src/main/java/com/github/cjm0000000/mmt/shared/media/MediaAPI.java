package com.github.cjm0000000.mmt.shared.media;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;

/**
 * media APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface MediaAPI {
  /**
   * Upload media to remote server
   * 
   * @param cfg
   * @param type
   * @param file
   * @param fileName
   * @return
   */
  JSONResponse uploadMedia(MmtConfig cfg, String type, byte[] file, String fileName);

  /**
   * download media from remote server
   * 
   * @param cfg
   * @param media_id
   * @return
   */
  JSONResponse downloadMedia(MmtConfig cfg, String media_id);
}
