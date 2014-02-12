package com.github.cjm0000000.shared.test.message;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.KeyEvent;
import com.github.cjm0000000.mmt.core.message.event.LocationEvent;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.persistence.EventRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test cases for event repository
 * @author lemon
 * @version 2.0
 *
 */
public class EventRepository_Test extends AbstractTester {
	@Autowired
	private EventRepository eventRepository;
	
	@Override
	public void defaultCase() {
		keyEventTest();
	}
	
	/**
	 * Test location event
	 */
	@Test
	public void locationEventTest(){
		LocationEvent event = new LocationEvent();
		event.setLatitude(12.123456D);
		event.setLongitude(21.654321D);
		event.setPrecision(230.423D);
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvLocationEvent(event));
		LocationEvent eventAfterInsert = eventRepository.getRecvLocationEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getLatitude(), eventAfterInsert.getLatitude(), 0.000001D);
		assertEquals(event.getLongitude(), eventAfterInsert.getLongitude(), 0.000001D);
		assertEquals(event.getPrecision(), eventAfterInsert.getPrecision(), 0.000001D);
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}
	
	/**
	 * Test scan event
	 */
	@Test
	public void scanEventTest(){
		ScanEvent event = new ScanEvent();
		event.setEventType(EventType.scan);
		event.setTicket("TIC" + UUID.randomUUID().toString());
		event.setEventKey("KEY" + UUID.randomUUID().toString());
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvScanEvent(event));
		ScanEvent eventAfterInsert = eventRepository.getRecvScanEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getTicket(), eventAfterInsert.getTicket());
		assertEquals(event.getEventKey(), eventAfterInsert.getEventKey());
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}
	
	/**
	 * Test subscribe(scan) event
	 */
	@Test
	public void scanEventForSubscribeTest(){
		ScanEvent event = new ScanEvent();
		event.setEventType(EventType.subscribe);
		event.setTicket("TIC" + UUID.randomUUID().toString());
		event.setEventKey("qrscene_" + UUID.randomUUID().toString());
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvScanEvent(event));
		ScanEvent eventAfterInsert = eventRepository.getRecvScanEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getTicket(), eventAfterInsert.getTicket());
		assertEquals(event.getEventKey(), eventAfterInsert.getEventKey());
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}
	
	/**
	 * Test subscribe(simple) event
	 */
	@Test
	public void simpleEventForSubscribe(){
		SimpleEvent event = new SimpleEvent();
		event.setEventType(EventType.subscribe);
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvSimpleEvent(event));
		SimpleEvent eventAfterInsert = eventRepository.getRecvSimpleEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}
	
	/**
	 * Test Unsubscribe(simple) event
	 */
	@Test
	public void simpleEventForUnsubscribe(){
		SimpleEvent event = new SimpleEvent();
		event.setEventType(EventType.unsubscribe);
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvSimpleEvent(event));
		SimpleEvent eventAfterInsert = eventRepository.getRecvSimpleEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}
	
	/**
	 * Test key event
	 */
	private void keyEventTest(){
		KeyEvent event = new KeyEvent();
		String eventKey = "EventKey" + UUID.randomUUID().toString();
		event.setEventType(EventType.CLICK);
		event.setEventKey(eventKey);
		final long eventId = saveEventDetail(event);
		assertNotEquals(0, eventRepository.saveRecvKeyEvent(event));
		KeyEvent eventAfterInsert = eventRepository.getRecvKeyEvent(eventId);
		assertNotNull(eventAfterInsert);
		assertBaseMsg(event, eventAfterInsert);
		assertEquals(event.getEventKey(), eventAfterInsert.getEventKey());
		assertEquals(event.getEventType(), eventAfterInsert.getEventType());
	}

	/**
	 * 生成SimpleEvent
	 * @param msg
	 */
	private void prepareSimpleEvent(BaseMessage msg) {
		msg.setCreateTime(String.valueOf((System.currentTimeMillis() / 1000)));
		msg.setCust_id(CUST_ID);
		msg.setFromUserName("FROM-" + UUID.randomUUID().toString());
		msg.setId(IdWorkerManager.getIdWorker(BaseMessage.class).getId());
		msg.setService_type(ServiceType.OTHER);
		msg.setToUserName("TO-" + UUID.randomUUID().toString());
	}
	
	/**
	 * 比对基本信息
	 * @param before
	 * @param after
	 */
	private void assertBaseMsg(BaseMessage before, BaseMessage after){
		assertEquals(before.getCreateTime(), after.getCreateTime());
		assertEquals(before.getCust_id(), after.getCust_id());
		assertEquals(before.getFromUserName(), after.getFromUserName());
		assertEquals(before.getService_type(), after.getService_type());
		assertEquals(before.getToUserName(), after.getToUserName());
	}
	
	/**
	 * 保存基本信息
	 * @param msg
	 * @return
	 */
	private long saveEventDetail(BaseMessage msg){
		prepareSimpleEvent(msg);
		assertNotEquals(0, eventRepository.saveRecvEventDetail(msg));
		return msg.getId();
	}

}
