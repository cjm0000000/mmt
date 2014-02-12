package com.github.cjm0000000.mmt.weixin.api.initiative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.access.AccessTokenService;
import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.send.initiative.VideoMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.VoiceMessage;
import com.github.cjm0000000.mmt.shared.message.process.AbstractInitiativeMsgProcessor;

/**
 * WeiXin initiative message processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
@Service("weiXinInitiativeMsgProcessor")
public final class WeiXinInitiativeMsgProcessor extends AbstractInitiativeMsgProcessor {
  @Autowired
  private WeiXinCommonAPI commonAPI;

  @Override
  public AccessTokenService getAccessTokenService() {
    return commonAPI;
  }

  @Override
  public JSONResponse sendVoiceMsg(MmtConfig cfg, VoiceMessage msg) {
    return sendMsg(cfg, msg);
  }

  @Override
  public JSONResponse sendVideoMsg(MmtConfig cfg, IVideo msg) {
    if (msg == null) commonAPI.sendError("VideoMessage is null.");
    if (!(msg instanceof VideoMessage)) commonAPI.sendError("内部错误，不是有效的微信VideoMessage。");
    VideoMessage message = (VideoMessage) msg;
    return sendMsg(cfg, message);
  }

}
