package com.github.cjm0000000.mmt.shared.message.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.access.Access;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.process.PassiveMsgProcessor;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.shared.access.persistence.AccessRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;
import com.github.cjm0000000.mmt.shared.toolkit.secure.SecureUtil;

/**
 * abstract passive processor<br>
 * for task mapping and permission check
 * 
 * @author lemon
 * @version 2.0
 * 
 */
abstract class AbstractPassiveProcessor implements PassiveMsgProcessor {
  private static final Logger logger = Logger.getLogger(AbstractPassiveProcessor.class);
  @Autowired
  private AccessRepository accessRepository;

  @Override
  public final BaseMessage process(String mmt_token, BaseMessage recvMsg) {
    MmtConfig cfg = getConfig(mmt_token);
    if (cfg == null) throw new MmtException("MMT config is null.");
    if (logger.isDebugEnabled()) logger.debug("MMT configure is " + cfg);

    recvMsg.setCust_id(cfg.getCust_id());
    recvMsg.setService_type(getServiceType());

    if (recvMsg instanceof SimpleRecvMessage) // process passive message
      return processMessage(cfg, (SimpleRecvMessage) recvMsg);

    if (recvMsg instanceof SimpleEvent) // process passive event
      return processEvent(cfg, (SimpleEvent) recvMsg);

    throw new MmtException("No such message instance.", new ClassCastException());
  }

  @Override
  public final boolean verifySignature(Access sa) {
    if (null == sa || sa.getSignature() == null) return false;
    // save log
    sa.setService_type(getServiceType());
    sa.setId(IdWorkerManager.getIdWorker(Access.class).getId());
    // remember save access log when echostr is not null
    if (sa.getEchostr() != null) accessRepository.saveAccessLog(sa);
    // nonce,token,timestamp dictionary sort
    List<String> list = new ArrayList<>(3);
    list.add(sa.getNonce());
    list.add(sa.getToken());
    list.add(sa.getTimestamp_api());
    Collections.sort(list);
    StringBuilder sb = new StringBuilder();
    for (String str : list) {
      sb.append(str);
    }
    // sha1 for signature
    String sha1str = SecureUtil.sha1(sb.toString());
    if (logger.isDebugEnabled()) {
      logger.debug("rsult for SHA1:" + sha1str);
      logger.debug("signature:" + sa.getSignature());
    }
    return sha1str.equalsIgnoreCase(sa.getSignature());
  }

  /**
   * get MMT configure
   * 
   * @param mmt_token
   * @return
   */
  protected abstract MmtConfig getConfig(String mmt_token);

  /**
   * process event
   * 
   * @param cfg
   * @param event
   * @return
   */
  protected abstract BaseMessage processEvent(MmtConfig cfg, SimpleEvent event);

  /**
   * process message
   * 
   * @param cfg
   * @param msg
   * @return
   */
  protected abstract BaseMessage processMessage(MmtConfig cfg, SimpleRecvMessage msg);
}
