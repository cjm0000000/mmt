package com.github.cjm0000000.mmt.weixin.api.initiative;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.customer.AbstractCustomMenuAPI;
import com.github.cjm0000000.mmt.weixin.config.AccountType;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;

/**
 * Custom menu implement for WeiXin
 * 
 * @author lemon
 * @version 2.0
 * 
 */
@Service("weiXinCustomMenuAPI")
public class WeiXinCustomMenuProcessor extends AbstractCustomMenuAPI {
  private static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
  // private static final String MENU_SEARCH_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
  private static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete";

  private WeiXinCommonAPI commonAPI;

  @Override
  public ServiceType getServiceType() {
    return ServiceType.WEIXIN;
  }

  @Override
  public Map<String, Object> getAccessTokenRequestParams(MmtConfig config) {
    return commonAPI.getAccessTokenRequestParams(config);
  }

  @Override
  public void sendError(String errorMsg) {
    commonAPI.sendError(errorMsg);
  }

  @Override
  public String getCreateMenuUrl() {
    return MENU_CREATE_URL;
  }

  @Override
  public String getDeleteMenuUrl() {
    return MENU_DELETE_URL;
  }

  @Override
  public void verifyConfig(MmtConfig config) {
    if (config == null) sendError("客户微信配置信息不存在。");
    commonAPI.checkConfigType(config);
    WeiXinConfig cfg = (WeiXinConfig) config;
    if (cfg.getAccount_type().equals(AccountType.DY)) sendError("订阅号不支持自定义菜单操作。");
  }

  @Override
  public String getCommonUrl() {
    return commonAPI.getCommonUrl();
  }

}
