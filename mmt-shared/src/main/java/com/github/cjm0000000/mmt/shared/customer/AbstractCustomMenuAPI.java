package com.github.cjm0000000.mmt.shared.customer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuLog.Action;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomMenuRepository;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeProcessor;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;

/**
 * abstract custom menu processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public abstract class AbstractCustomMenuAPI extends AbstractInitiativeProcessor
    implements
      CustomMenuAPI {
  private static final Logger logger = Logger.getLogger(AbstractCustomMenuAPI.class);
  @Autowired
  private CustomMenuRepository customMenuRepository;

  /**
   * 获取创建菜单接口URL
   * 
   * @return
   */
  public abstract String getCreateMenuUrl();

  /**
   * 获取删除菜单接口URL
   * 
   * @return
   */
  public abstract String getDeleteMenuUrl();

  /**
   * 验证接口配置
   * 
   * @param config
   * @return
   */
  public abstract void verifyConfig(MmtConfig config);

  @Override
  public ReturnCode createMenus(MmtConfig cfg, String menuJson) {
    // 验证配置
    verifyConfig(cfg);
    // 发送请求
    Map<String, Object> params = new HashMap<>();
    params.put("access_token", getAccessToken(cfg));
    String result = HttpConnector.post(getCreateMenuUrl(), menuJson, params);
    if (logger.isDebugEnabled()) logger.debug("创建自定义菜单成功：" + result);
    // save log
    CustomMenuLog log =
        generateCustomMenuLog(params.get("access_token").toString(), Action.CREATE,
            cfg.getCust_id(), menuJson, result);
    customMenuRepository.saveMenuSyncLog(log);
    // parser result
    JSONObject json = JSON.parseObject(result);
    return JSON.toJavaObject(json, ReturnCode.class);
  }

  @Override
  public String getMenus(MmtConfig cfg) {
    // 暂时可以不用实现
    return null;
  }

  @Override
  public ReturnCode deleteMenus(MmtConfig cfg) {
    if (cfg == null) throw new MmtException("客户接口配置信息不存在。");
    // 发送请求
    Map<String, Object> params = new HashMap<>();
    params.put("access_token", getAccessToken(cfg));
    String result = HttpConnector.post(getDeleteMenuUrl(), params);
    // save log
    CustomMenuLog log =
        generateCustomMenuLog(params.get("access_token").toString(), Action.DELETE,
            cfg.getCust_id(), null, result);
    customMenuRepository.saveMenuSyncLog(log);
    JSONObject json = JSON.parseObject(result);
    return JSON.toJavaObject(json, ReturnCode.class);
  }

  @Override
  public void sendError(String errorMsg) {
    throw new MmtException(errorMsg);
  }

  /**
   * 拼装成LOG
   * 
   * @param access_token
   * @param action
   * @param cust_id
   * @param result
   * @return
   */
  private CustomMenuLog generateCustomMenuLog(String access_token, Action action, int cust_id,
      String msg, String result) {
    CustomMenuLog log = new CustomMenuLog();
    log.setAccess_token(access_token);
    log.setAction(action);
    log.setCust_id(cust_id);
    log.setMsg(msg);
    log.setResult(result);
    log.setService_type(getServiceType());
    log.setId(IdWorkerManager.getIdWorker(CustomMenuLog.class).getId());
    return log;
  }

}
