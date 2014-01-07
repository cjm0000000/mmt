package com.github.cjm0000000.mmt.weixin.api.initiative;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.core.access.AccessTokenService;
import com.github.cjm0000000.mmt.core.access.AccessTokenServiceManager;
import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.fans.Fans;
import com.github.cjm0000000.mmt.shared.fans.remote.FansAPI;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;

/**
 * WeiXin fans APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class WeiXinFansAPI implements AccessTokenServiceManager, FansAPI {
  private static final Logger logger = Logger.getLogger(WeiXinFansAPI.class);
  private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

  @Autowired
  private WeiXinCommonAPI commonApi;

  @Override
  public Fans getUserInfo(MmtConfig cfg, String openId) {
    if (logger.isDebugEnabled()) logger.debug("try to get user infomation from remote server.");
    String resp = HttpConnector.get(USER_INFO_URL, getUserInfoRequestParams(cfg, openId));
    if (logger.isDebugEnabled()) logger.debug("get userInfo response: " + resp);
    JSONObject jsonObj = JSON.parseObject(resp);
    // TODO 封装Fans
    if (jsonObj.get("subscribe") != null && jsonObj.get("openid") != null)
      return JSON.toJavaObject(jsonObj, null);
    return null;
  }

  @Override
  public JSONResponse getFans(MmtConfig cfg, String next_openid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AccessTokenService getAccessTokenService() {
    return commonApi;
  }

  /**
   * 获取用户基本信息请求参数
   * 
   * @param cfg
   * @param openId
   * @return
   */
  private Map<String, Object> getUserInfoRequestParams(MmtConfig cfg, String openId) {
    Map<String, Object> params = new HashMap<>(4);
    params.put("access_token", getAccessTokenService().getAccessToken(cfg));
    params.put("openid", openId);
    return params;
  }

}
