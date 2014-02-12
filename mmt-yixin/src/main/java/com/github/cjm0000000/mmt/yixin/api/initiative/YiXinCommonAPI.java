package com.github.cjm0000000.mmt.yixin.api.initiative;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeProcessor;
import com.github.cjm0000000.mmt.yixin.YiXinException;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;

/**
 * YiXin common APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
@Service
public final class YiXinCommonAPI extends AbstractInitiativeProcessor {
  private static final Logger logger = Logger.getLogger(YiXinCommonAPI.class);
  private static final String COMMON_URL = "https://api.yixin.im/cgi-bin/token";

  /**
   * 检测是否是有效的WeiXinConfig
   * 
   * @param config
   */
  public void checkConfigType(MmtConfig config) {
    if (!(config instanceof YiXinConfig))
      throw new YiXinException("内部错误，请联系管理员。", new ClassCastException("config不是YiXinConfig类型"));
  }

  @Override
  public ServiceType getServiceType() {
    return ServiceType.YIXIN;
  }

  @Override
  public Map<String, Object> getAccessTokenRequestParams(MmtConfig config) {
    YiXinConfig cfg = (YiXinConfig) config;
    // 请求参数
    Map<String, Object> params = new HashMap<>();
    params.put("grant_type", "client_credential");
    params.put("appid", cfg.getAppid());
    params.put("secret", cfg.getSecret());
    if (logger.isDebugEnabled()) logger.debug("Access token request params: " + params);
    return params;
  }

  @Override
  public String getCommonUrl() {
    return COMMON_URL;
  }

  @Override
  public void sendError(String errorMsg) {
    throw new YiXinException(errorMsg);
  }

}
