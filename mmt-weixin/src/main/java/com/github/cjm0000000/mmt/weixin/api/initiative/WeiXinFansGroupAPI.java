package com.github.cjm0000000.mmt.weixin.api.initiative;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.core.access.AccessTokenService;
import com.github.cjm0000000.mmt.core.access.AccessTokenServiceManager;
import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.fans.FansGroup;
import com.github.cjm0000000.mmt.shared.fans.remote.FansGroupAPI;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;

/**
 * WeiXin fans group APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class WeiXinFansGroupAPI implements AccessTokenServiceManager, FansGroupAPI {
  private static final Logger logger = Logger.getLogger(WeiXinFansGroupAPI.class);
  private static final String GROUP_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/groups/create";
  private static final String GROUP_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/groups/get";
  private static final String GROUP_QUERY_BY_OPENID_URL =
      "https://api.weixin.qq.com/cgi-bin/groups/getid";
  private static final String GROUP_MODIFY_URL = "https://api.weixin.qq.com/cgi-bin/groups/update";
  private static final String CHANGE_FANS_GROUP_URL =
      "https://api.weixin.qq.com/cgi-bin/groups/members/update";
  @Autowired
  private WeiXinCommonAPI commonApi;

  @Override
  public JSONResponse createGroup(MmtConfig cfg, FansGroup group) {
    commonApi.checkConfigType(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to create a group in remote server[cust_id=" + cfg.getCust_id()
          + ", groupName=" + group.getGroup_name() + "]");
    String resp =
        HttpConnector.post(getValidUrl(cfg, GROUP_CREATE_URL), MmtJSONParser.toJSON(group));
    if (logger.isDebugEnabled()) logger.debug("get create group response: " + resp);
    JSONObject jsonObj = JSON.parseObject(resp);
    if (jsonObj.get("group") != null) // success
      return JSON.toJavaObject(jsonObj, FansGroup.class);
    return JSON.toJavaObject(jsonObj, ReturnCode.class);
  }

  @Override
  public List<FansGroup> getGroups(MmtConfig cfg) {
    commonApi.checkConfigType(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to query groups from remote server[cust_id=" + cfg.getCust_id() + "]");
    String resp = HttpConnector.get(getValidUrl(cfg, GROUP_QUERY_URL));
    if (logger.isDebugEnabled()) logger.debug("get query group response: " + resp);
    JSONObject jsonObj = JSON.parseObject(resp);
    if (jsonObj.get("group") != null) {// success
      // TODO 组装成List
    }
    return null;
  }

  @Override
  public JSONResponse getGroupByOpenId(MmtConfig cfg, String openId) {
    commonApi.checkConfigType(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to get group by open id from remote server[cust_id=" + cfg.getCust_id()
          + ", openId=" + openId + "]");
    String resp = HttpConnector.post(getValidUrl(cfg, GROUP_QUERY_BY_OPENID_URL));
    if (logger.isDebugEnabled()) logger.debug("get groupzByOpenId response: " + resp);
    JSONObject jsonObj = JSON.parseObject(resp);
    if (jsonObj.get("groupid") != null) // success
      return new FansGroup(jsonObj.getIntValue("groupid"), null);
    return JSON.toJavaObject(jsonObj, ReturnCode.class);
  }

  @Override
  public JSONResponse modifyGroupName(MmtConfig cfg, FansGroup group) {
    commonApi.checkConfigType(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to modify group name[group_id=" + group.getGroup_id() + ",group_name="
          + group.getGroup_name() + "]");
    String sendMsg = null;// TODO parser object to JSON
    String resp = HttpConnector.post(GROUP_MODIFY_URL, sendMsg);
    if (logger.isDebugEnabled()) logger.debug("get modifyGroupName response: " + resp);
    return (JSONResponse) MmtJSONParser.fromJSON(resp, ReturnCode.class);
  }

  @Override
  public JSONResponse changeFansGroup(MmtConfig cfg, String openId, String toGroupId) {
    commonApi.checkConfigType(cfg);
    if (logger.isDebugEnabled())
      logger.debug("try to change fans's group[cust_id=" + cfg.getCust_id() + ", openId=" + openId
          + ", toGroupId=" + toGroupId + "]");
    String msg = null;// TODO parser to JSON
    String resp = HttpConnector.post(getValidUrl(cfg, CHANGE_FANS_GROUP_URL), msg);
    if (logger.isDebugEnabled()) logger.debug("get changeFansGroup response: " + resp);
    return (JSONResponse) MmtJSONParser.fromJSON(resp, ReturnCode.class);
  }

  @Override
  public AccessTokenService getAccessTokenService() {
    return commonApi;
  }

  /**
   * get valid URL
   * 
   * @param cfg
   * @param url
   * @return
   */
  private String getValidUrl(MmtConfig cfg, String url) {
    return url + "?access_token=" + getAccessTokenService().getAccessToken(cfg);
  }

}
