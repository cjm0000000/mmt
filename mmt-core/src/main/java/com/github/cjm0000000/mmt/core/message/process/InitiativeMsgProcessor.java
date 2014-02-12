package com.github.cjm0000000.mmt.core.message.process;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.send.initiative.ImageMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.NewsMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.TextMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.VoiceMessage;

/**
 * Initiative message processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public interface InitiativeMsgProcessor {

  /**
   * send text message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendTextMsg(MmtConfig cfg, TextMessage msg);

  /**
   * send image message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendImageMsg(MmtConfig cfg, ImageMessage msg);

  /**
   * send voice message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendVoiceMsg(MmtConfig cfg, VoiceMessage msg);

  /**
   * send video message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendVideoMsg(MmtConfig cfg, IVideo msg);

  /**
   * send music message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendMusicMsg(MmtConfig cfg, MusicMessage msg);

  /**
   * send news message manual
   * 
   * @param cfg
   * @param msg
   * @return
   */
  JSONResponse sendNewsMsg(MmtConfig cfg, NewsMessage msg);
}
