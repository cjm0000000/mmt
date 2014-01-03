package com.github.cjm0000000.mmt.weixin.api.initiative;

import java.util.HashMap;
import java.util.Map;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuAPI;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeProcessor;
import com.github.cjm0000000.mmt.weixin.WeiXinException;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;

/**
 * Custom menu implement for WeiXin
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class CustomMenuProcessor extends AbstractInitiativeProcessor implements CustomMenuAPI {

  @Override
  public ServiceType getServiceType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReturnCode createMenus(String menuJson) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getMenus() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReturnCode deleteMenus() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Object> getAccessTokenRequestParams(MmtConfig config) {
    if (!(config instanceof WeiXinConfig))
      throw new WeiXinException("内部错误，请联系管理员。", new ClassCastException("config不是WeiXinConfig类型"));
    WeiXinConfig cfg = (WeiXinConfig) config;
    // 请求参数
    Map<String, Object> params = new HashMap<>();
    params.put("grant_type", "client_credential");
    params.put("appid", cfg.getAppid());
    params.put("secret", cfg.getSecret());
    return params;
  }

  @Override
  public void sendError(String errorMsg) {
    throw new WeiXinException(errorMsg);
  }

}
