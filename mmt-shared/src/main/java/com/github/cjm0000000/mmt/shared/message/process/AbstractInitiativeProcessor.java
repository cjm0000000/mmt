package com.github.cjm0000000.mmt.shared.message.process;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.core.access.AccessToken;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;
import com.github.cjm0000000.mmt.shared.access.AccessTokenLog;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.access.persistence.AccessRepository;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;

/**
 * abstract initiative processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public abstract class AbstractInitiativeProcessor implements InitiativeProcessor {
  private static final Logger logger = Logger.getLogger(AbstractInitiativeProcessor.class);
  @Autowired
  private AccessRepository accessRepository;

  /**
   * 获取AccessToken请求的参数
   * 
   * @param mmt_token
   * @return
   */
  public abstract Map<String, Object> getAccessTokenRequestParams(MmtConfig config);

  /**
   * 获取通用接口URL
   * 
   * @return
   */
  public abstract String getCommonUrl();

  /**
   * 发送错误信息
   * 
   * @param errorMsg
   */
  public abstract void sendError(String errorMsg);

  @Override
  public String getAccessToken(MmtConfig cfg) {
    if (logger.isDebugEnabled()) logger.debug("try to get access token from database.");
    // 从数据库读取Access Token
    AccessToken token = accessRepository.getAccessToken(cfg.getCust_id(), getServiceType());
    // 增加10秒：一次请求的时间可能需要花去5S
    if (token != null && token.getExpire_time() >= ((System.currentTimeMillis() / 1000) + 10)) {
      if (logger.isDebugEnabled())
        logger
            .debug("get access token from database[access_token=" + token.getAccess_token() + "]");
      return token.getAccess_token();
    }
    // 请求参数
    Map<String, Object> params = getAccessTokenRequestParams(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to get access from remote server[params=" + params + "]");
    // 获取结果
    String result = HttpConnector.get(getCommonUrl(), params);
    if (logger.isDebugEnabled())
      logger.debug("get access token request successfully. result=" + result);
    // save log
    AccessTokenLog log = new AccessTokenLog();
    log.setCust_id(cfg.getCust_id());
    log.setAppid(params.get("appid").toString());
    log.setGrant_type(params.get("grant_type").toString());
    log.setSecret(params.get("secret").toString());
    log.setResult(result);
    log.setService_type(getServiceType());
    log.setId(IdWorkerManager.getIdWorker(AccessTokenLog.class).getId());
    accessRepository.saveAccessTokenLog(log);
    // parser result
    JSONObject jsonObj = JSON.parseObject(result);
    if (jsonObj.get("errcode") != null) {
      ReturnCode rCode = JSON.toJavaObject(jsonObj, ReturnCode.class);
      sendError("Get Access Token faild: " + rCode.getErrmsg());
    }
    token = JSON.toJavaObject(jsonObj, AccessToken.class);
    // 保存
    token.setCust_id(cfg.getCust_id());
    token.setExpire_time((int) (System.currentTimeMillis() / 1000) + token.getExpires_in());
    token.setService_type(getServiceType());
    accessRepository.deleteAccessToken(cfg.getCust_id(), getServiceType());
    accessRepository.saveAccessToken(token);
    return token.getAccess_token();
  }
}
