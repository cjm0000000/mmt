package com.github.cjm0000000.mmt.weixin.api.initiative;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuAPI;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeProcessor;

/**
 * Custom menu implement for WeiXin
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
  public MmtConfig getConfig(String mmt_token) {
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

}
