package com.github.cjm0000000.mmt.weixin.api.initiative;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeProcessor;
import com.github.cjm0000000.mmt.weixin.WeiXinException;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;

/**
 * WeiXin common APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
@Service
public final class WeiXinCommonAPI extends AbstractInitiativeProcessor {
  private static final Logger logger = Logger.getLogger(WeiXinCommonAPI.class);
  private static final String COMMON_URL = "https://api.weixin.qq.com/cgi-bin/token";

  @Override
  public void checkConfigType(MmtConfig config) {
    if (!(config instanceof WeiXinConfig))
      throw new WeiXinException("内部错误，请联系管理员。", new ClassCastException("config不是WeiXinConfig类型"));
  }

  @Override
  public ServiceType getServiceType() {
    return ServiceType.WEIXIN;
  }

  @Override
  public Map<String, Object> getAccessTokenRequestParams(MmtConfig config) {
    WeiXinConfig cfg = (WeiXinConfig) config;
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
    throw new WeiXinException(errorMsg);
  }

}
