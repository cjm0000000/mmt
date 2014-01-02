package com.github.cjm0000000.mmt.yixin.message.process;

import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.MusicMessage;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.process.AbstractPassiveMsgProcessor;
import com.github.cjm0000000.mmt.yixin.YiXin;
import com.github.cjm0000000.mmt.yixin.YiXinException;

/**
 * 简单的易信消息机器人
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service
public class SimpleYiXinMsgProcessor extends AbstractPassiveMsgProcessor {

  @Override
  public final ServiceType getServiceType() {
    return ServiceType.YIXIN;
  }

  @Override
  public final MmtConfig getConfig(String mmt_token) {
    return YiXin.getConfig(mmt_token);
  }

  @Override
  public BaseMessage processAudioMsg(MmtConfig cfg, AudioMessage msg) {
    //默认提示消息国际化
    return sendTextMessage(msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
  }

  @Override
  public BaseMessage processImageMsg(MmtConfig cfg, ImageMessage msg) {
    return sendTextMessage(msg, "亲，我暂时无法识别图片信息哦，您可以给我发文字消息。");
  }

  @Override
  public BaseMessage processLinkMsg(MmtConfig cfg, LinkMessage msg) {
    return sendTextMessage(msg, "亲，我暂时无法识别链接信息哦，您可以给我发文字消息。");
  }

  @Override
  public BaseMessage processLocationMsg(MmtConfig cfg, LocationMessage msg) {
    return sendTextMessage(msg, "亲，我暂时无法识别地理位置信息哦，您可以给我发文字消息。");
  }
  
  @Override
  public BaseMessage processMusicMsg(MmtConfig cfg, MusicMessage msg) {
    return sendTextMessage((BaseMessage) msg, "亲，我暂时无法识别音乐消息哦，您可以给我发文字消息。");
  }

  @Override
  public BaseMessage processVideoMsg(MmtConfig cfg, IVideo msg) {
    return sendTextMessage((BaseMessage) msg, "亲，我暂时无法识别视频消息哦，您可以给我发文字消息。");
  }

  @Override
  public final BaseMessage processVoiceMsg(MmtConfig cfg, VoiceMessage msg) {
    throw new YiXinException("拜托，这里是易信，哪来的Voice消息。");
  }

}
