package com.github.cjm0000000.mmt.weixin.api.initiative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.access.AccessTokenService;
import com.github.cjm0000000.mmt.shared.media.AbstractMediaAPI;

/**
 * WeiXin media APIs
 * 
 * @author lemon
 * @version 2.0
 * 
 */
@Service("weiXinMediaAPI")
public class WeiXinMediaAPI extends AbstractMediaAPI {
  private static final String MEDIA_UPLOAD_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
  //private static final String MEDIA_DOWNLOAD_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
  @Autowired
  private WeiXinCommonAPI commonAPI;

  @Override
  public AccessTokenService getAccessTokenService() {
    return commonAPI;
  }

  @Override
  public String getUploadMediaUrl() {
    return MEDIA_UPLOAD_URL;
  }

}
