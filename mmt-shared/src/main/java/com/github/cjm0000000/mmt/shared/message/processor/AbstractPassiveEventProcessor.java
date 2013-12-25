package com.github.cjm0000000.mmt.shared.message.processor;

import org.apache.log4j.Logger;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.KeyEvent;
import com.github.cjm0000000.mmt.core.message.event.LocationEvent;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.message.processor.PassiveMsgProcessor;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * passive event processor
 * @author lemon
 * @version 2.0
 *
 */
abstract class AbstractPassiveEventProcessor implements PassiveMsgProcessor {
	private static final Logger logger = Logger.getLogger(AbstractPassiveEventProcessor.class);
	
	/**
	 * get MMT configure
	 * @param mmt_token
	 * @return
	 */
	protected abstract MmtConfig getConfig(String mmt_token);
	
	/**
	 * process message
	 * @param msg
	 * @return
	 */
	protected abstract BaseMessage processMessage(SimpleRecvMessage msg);
	
	@Override
	public final BaseMessage process(String mmt_token, MmtService recvMsg) {
		MmtConfig cfg = getConfig(mmt_token);
		if(cfg == null)
			throw new MmtException("MMT config is null.");
		if(logger.isDebugEnabled())
			logger.debug("MMT configure is " + cfg);
		
		recvMsg.setCust_id(cfg.getCust_id());
		recvMsg.setService_type(getServiceType());
		
		if(recvMsg instanceof SimpleRecvMessage) // process passive message
			return processMessage((SimpleRecvMessage) recvMsg);
		
		if(recvMsg instanceof SimpleEvent) // process passive event
			return processEvent((SimpleEvent) recvMsg);
		
		throw new MmtException("No such message instance.", new ClassCastException());
	}
	
	/**
	 * 点击菜单
	 * @param event
	 * @return
	 */
	protected BaseMessage doClick(KeyEvent event){
		//TODO 完成Click 事件
		return null;
	}
	
	/**
	 * 扫二维码
	 * @param event
	 * @return
	 */
	protected BaseMessage doScan(ScanEvent event){
		
		return null;
	}
	
	/**
	 * do subscribe
	 * @param event
	 * @return
	 */
	protected BaseMessage doSubscribe(SimpleEvent event){
		preSubscribe(event);
		//TODO doSubscribe
		return null;
	}
	
	/**
	 * do unsubscribe
	 * @param event
	 * @return
	 */
	protected BaseMessage doUnsubscribe(SimpleEvent event){
		//TODO doUnSubscribe
		return null;
	}
	
	/**
	 * pre subscribe
	 * @param event
	 * @return
	 */
	protected void preSubscribe(SimpleEvent event){
		//TODO pre Subscribe
	}
	
	/**
	 * 上报地理位置
	 * @param event
	 * @return
	 */
	protected BaseMessage reportLocation(LocationEvent event){
		//TODO 完成地理位置上报功能
		return null;
	}
	
	/**
	 * process event
	 * @param event
	 * @return
	 */
	private BaseMessage processEvent(SimpleEvent event){
		if(logger.isDebugEnabled())
			logger.debug("process event[eventType=" + event.getEventType() + "]");
		EventType eventType = event.getEventType();
		if(EventType.subscribe.equals(eventType)){//subscribe 
			BaseMessage resMsg = doSubscribe(event);
			if(event instanceof ScanEvent){
				//参数过滤 qrscene_
				ScanEvent scan = (ScanEvent) event;
				scan.setEventKey(scan.getEventKey().replaceAll("qrscene_", ""));
				return doScan(scan);
			}
			return resMsg;
		}
		if(EventType.unsubscribe.equals(eventType))
			return doUnsubscribe(event);
		if(EventType.scan.equals(eventType) && event instanceof ScanEvent)
			return doScan((ScanEvent) event);
		if(EventType.LOCATION.equals(eventType) && event instanceof LocationEvent)
			return reportLocation((LocationEvent) event);
		if(EventType.CLICK.equals(eventType) && event instanceof KeyEvent)
			doClick((KeyEvent) event);
		logger.error("No event type found!!!");
		throw new MmtException("没有找到对应的事件。");
	}
	
}
