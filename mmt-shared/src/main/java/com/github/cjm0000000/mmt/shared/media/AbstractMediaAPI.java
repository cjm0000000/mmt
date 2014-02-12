package com.github.cjm0000000.mmt.shared.media;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.core.access.AccessTokenServiceManager;
import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;

/**
 * abstract media APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public abstract class AbstractMediaAPI implements AccessTokenServiceManager, MediaAPI {
  private static final Logger logger = Logger.getLogger(AbstractMediaAPI.class);

  /**
   * 获取上传多媒体文件的URL
   * 
   * @return
   */
  public abstract String getUploadMediaUrl();

  @Override
  public JSONResponse uploadMedia(MmtConfig cfg, String type, byte[] file, String fileName) {
    // try upload
    if (logger.isDebugEnabled())
      logger.debug("try to upload a media file[type=" + type + ",fileName=" + fileName + "]");
    String resp =
        HttpConnector.uploadFile(getUploadMediaUrl(), getUploadMediaParams(type, cfg), file,
            fileName);
    if (logger.isDebugEnabled()) logger.debug("receive a response: " + resp);
    JSONObject jsonObj = JSONObject.parseObject(resp);
    if (jsonObj.get("type") != null && jsonObj.get("media_id") != null) {// success
      return JSON.toJavaObject(jsonObj, MediaResponse.class);
    }
    return JSON.toJavaObject(jsonObj, ReturnCode.class);
  }

  @Override
  public JSONResponse downloadMedia(MmtConfig cfg, String media_id) {
    // 暂时可以先不实现
    return null;
  }

  /**
   * 获取上传多媒体文件的参数
   * 
   * @param type
   * @param cfg
   * @return
   */
  private Map<String, Object> getUploadMediaParams(String type, MmtConfig cfg) {
    Map<String, Object> params = new HashMap<>(4);
    params.put("access_token", getAccessTokenService().getAccessToken(cfg));
    params.put("type", type);
    return params;
  }

}
