package com.github.cjm0000000.mmt.shared.message.process;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.KeyEvent;
import com.github.cjm0000000.mmt.core.message.event.LocationEvent;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.send.passive.TextMessage;
import com.github.cjm0000000.mmt.shared.fans.FansManager;
import com.github.cjm0000000.mmt.shared.message.MsgManager;

/**
 * passive event processor
 * 
 * @author lemon
 * @version 2.0
 * 
 */
abstract class AbstractPassiveEventProcessor extends AbstractPassiveProcessor {
  private static final Logger logger = Logger.getLogger(AbstractPassiveEventProcessor.class);
  @Autowired
  private FansManager fansManager;
  @Autowired
  protected MsgManager msgManager;

  /**
   * process event
   * 
   * @param cfg
   * @param event
   * @return
   */
  public final BaseMessage processEvent(MmtConfig cfg, SimpleEvent event) {
    if (logger.isDebugEnabled())
      logger.debug("process event[eventType=" + event.getEventType() + "]");
    EventType eventType = event.getEventType();
    if (EventType.subscribe.equals(eventType)) {// subscribe
      BaseMessage resMsg = doSubscribe(cfg, event);
      if (event instanceof ScanEvent) {
        // 参数过滤 qrscene_
        ScanEvent scan = (ScanEvent) event;
        scan.setEventKey(scan.getEventKey().replaceAll("qrscene_", ""));
        return doScan(scan);
      }
      return resMsg;
    }
    if (EventType.unsubscribe.equals(eventType)) return doUnsubscribe(cfg, event);
    if (EventType.scan.equals(eventType) && event instanceof ScanEvent)
      return doScan((ScanEvent) event);
    if (EventType.LOCATION.equals(eventType) && event instanceof LocationEvent)
      return reportLocation((LocationEvent) event);
    if (EventType.CLICK.equals(eventType) && event instanceof KeyEvent) doClick((KeyEvent) event);
    logger.error("No event type found!!!");
    throw new MmtException("没有找到对应的事件。");
  }

  /**
   * 点击菜单
   * 
   * @param event
   * @return
   */
  protected BaseMessage doClick(KeyEvent event) {
    // TODO 完成Click 事件
    return null;
  }

  /**
   * 扫二维码
   * 
   * @param event
   * @return
   */
  protected BaseMessage doScan(ScanEvent event) {

    return null;
  }

  /**
   * do subscribe
   * 
   * @param cfg
   * @param event
   * @return
   */
  protected BaseMessage doSubscribe(MmtConfig cfg, SimpleEvent event) {
    // 预处理
    preSubscribe(cfg, event);
    TextMessage replyMsg = new TextMessage();
    buildReplyMsg(event, replyMsg);
    replyMsg.setContent(cfg.getSubscribe_msg());
    return replyMsg;
  }

  /**
   * do unsubscribe
   * 
   * @param cfg
   * @param event
   * @return
   */
  protected BaseMessage doUnsubscribe(MmtConfig cfg, SimpleEvent event) {
    // update fans information
    fansManager.disableFans(cfg.getCust_id(), getServiceType(), event.getFromUserName());
    return null;
  }

  /**
   * pre subscribe
   * 
   * @param cfg
   * @param event
   */
  protected void preSubscribe(MmtConfig cfg, SimpleEvent event) {
    // save fans
    fansManager.saveFans(fansManager.newFans(cfg.getCust_id(), event.getFromUserName(),
        getServiceType()));
  }

  /**
   * 上报地理位置
   * 
   * @param event
   * @return
   */
  protected BaseMessage reportLocation(LocationEvent event) {
    // TODO 完成地理位置上报功能
    return null;
  }

  /**
   * 发送文本消息
   * 
   * @param msg
   * @param content
   * @return
   */
  protected BaseMessage sendTextMessage(BaseMessage msg, String content) {
    TextMessage replyMsg = new TextMessage();
    buildReplyMsg(msg, replyMsg);
    replyMsg.setContent(content);
    // save log
    msgManager.saveSendTextMsg(replyMsg);
    return replyMsg;
  }

  /**
   * Build replay message<BR>
   * set values for nodes: ToUserName/FromUserName/CreateTime
   * 
   * @param recvMsg
   * @param replyMsg
   */
  protected void buildReplyMsg(BaseMessage recvMsg, BaseMessage replyMsg) {
    replyMsg.setCust_id(recvMsg.getCust_id());
    replyMsg.setService_type(getServiceType());
    replyMsg.setToUserName(recvMsg.getFromUserName());
    replyMsg.setFromUserName(recvMsg.getToUserName());
    replyMsg.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
  }

}
