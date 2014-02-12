package com.github.cjm0000000.mmt.shared.fans;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.access.JSONResponse;

/**
 * Fans group bean
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class FansGroup implements JSONResponse {
  @JSONField(name = "id")
  private int group_id;
  @JSONField(name = "name")
  private String group_name;

  public FansGroup() {}

  public FansGroup(int group_id, String group_name) {
    this.group_id = group_id;
    this.group_name = group_name;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public String getGroup_name() {
    return group_name;
  }

  public void setGroup_name(String group_name) {
    this.group_name = group_name;
  }

}
