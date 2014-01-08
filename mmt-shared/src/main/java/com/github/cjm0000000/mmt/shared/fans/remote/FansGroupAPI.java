package com.github.cjm0000000.mmt.shared.fans.remote;

import java.util.List;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.fans.FansGroup;

/**
 * Fans group APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface FansGroupAPI {
  /**
   * create fans group to remote server
   * 
   * @param cfg
   * @param group
   * @return
   */
  JSONResponse createGroup(MmtConfig cfg, FansGroup group);

  /**
   * get fans group from remote server
   * 
   * @param cfg
   * @return
   */
  List<FansGroup> getGroups(MmtConfig cfg);

  /**
   * get group by fan's open id
   * 
   * @param cfg
   * @param openId
   * @return
   */
  JSONResponse getGroupByOpenId(MmtConfig cfg, String openId);

  /**
   * modify group name
   * 
   * @param cfg
   * @param group
   * @return
   */
  JSONResponse modifyGroupName(MmtConfig cfg, FansGroup group);

  /**
   * change fan's group
   * 
   * @param cfg
   * @param openId
   * @param toGroupId
   * @return
   */
  JSONResponse changeFansGroup(MmtConfig cfg, String openId, String toGroupId);
}
