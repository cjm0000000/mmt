package com.github.cjm0000000.mmt.weixin.api.initiative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.access.AccessTokenService;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
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
public final class WeiXinCustomMenuProcessor extends AbstractCustomMenuAPI {
  private static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
  // private static final String MENU_SEARCH_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
  private static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete";
  @Autowired
  private WeiXinCommonAPI commonAPI;

  @Override
  public String getCreateMenuUrl() {
    return MENU_CREATE_URL;
  }

  @Override
  public String getDeleteMenuUrl() {
    return MENU_DELETE_URL;
  }

  @Override
  public AccessTokenService getAccessTokenService() {
    return commonAPI;
  }

  @Override
  public void verifyConfig(MmtConfig config) {
    if (config == null) commonAPI.sendError("客户微信配置信息不存在。");
    commonAPI.checkConfigType(config);
    WeiXinConfig cfg = (WeiXinConfig) config;
    if (cfg.getAccount_type().equals(AccountType.DY)) commonAPI.sendError("订阅号不支持自定义菜单操作。");
  }

}
