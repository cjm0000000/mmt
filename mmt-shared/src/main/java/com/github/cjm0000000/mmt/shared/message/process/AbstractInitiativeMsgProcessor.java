package com.github.cjm0000000.mmt.shared.message.process;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.process.InitiativeMsgProcessor;
import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;
import com.github.cjm0000000.mmt.core.message.send.initiative.ImageMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.NewsMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.TextMessage;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;

/**
 * abstract initiative message processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public abstract class AbstractInitiativeMsgProcessor implements InitiativeMsgProcessor {
  private static final Logger logger = Logger.getLogger(AbstractInitiativeMsgProcessor.class);
  private static final String SEND_MSG_URL =
      "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

  /**
   * get real initiative processor
   * 
   * @return
   */
  public abstract InitiativeProcessor getInitiativeProcessor();

  /**
   * get send message URL
   * 
   * @param cfg
   * @return
   */
  protected String getSendMsgUrl(MmtConfig cfg) {
    return SEND_MSG_URL + getInitiativeProcessor().getAccessToken(cfg);
  }

  /**
   * send message for bottom layer
   * 
   * @param cfg
   * @param msg
   * @return
   */
  protected JSONResponse sendMsg(MmtConfig cfg, SimpleMessage msg) {
    String json = MmtJSONParser.toJSON(msg);
    if (logger.isDebugEnabled()) logger.debug("generate json message: " + json);
    String response = HttpConnector.post(getSendMsgUrl(cfg), json);
    if (logger.isDebugEnabled())
      logger.debug("send text message successfully. response= " + response);
    return (ReturnCode) MmtJSONParser.fromJSON(response, ReturnCode.class);
  }

  @Override
  public JSONResponse sendTextMsg(MmtConfig cfg, TextMessage msg) {
    return sendMsg(cfg, msg);
  }

  @Override
  public JSONResponse sendImageMsg(MmtConfig cfg, ImageMessage msg) {
    return sendMsg(cfg, msg);
  }

  @Override
  public JSONResponse sendMusicMsg(MmtConfig cfg, MusicMessage msg) {
    return sendMsg(cfg, msg);
  }

  @Override
  public JSONResponse sendNewsMsg(MmtConfig cfg, NewsMessage msg) {
    return sendMsg(cfg, msg);
  }

}
