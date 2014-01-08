package com.github.cjm0000000.mmt.shared.media;

import com.github.cjm0000000.mmt.core.access.JSONResponse;

/**
 * media synchronized response
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class MediaResponse implements JSONResponse {
  private String type;
  private String media_id;
  private int created_at;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMedia_id() {
    return media_id;
  }

  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }

  public int getCreated_at() {
    return created_at;
  }

  public void setCreated_at(int created_at) {
    this.created_at = created_at;
  }

}
