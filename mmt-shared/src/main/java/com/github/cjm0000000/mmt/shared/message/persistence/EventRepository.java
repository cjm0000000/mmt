package com.github.cjm0000000.mmt.shared.message.persistence;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.KeyEvent;
import com.github.cjm0000000.mmt.core.message.event.LocationEvent;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * event repository
 * @author lemon
 * @version 2.0
 *
 */
@Repository
public interface EventRepository {
	/**
	 * get receive event message
	 * @param cust_id
	 * @param service_type
	 * @param eventType
	 * @param start
	 * @param limit
	 * @return
	 */
	/*@Lang(RawLanguageDriver.class)
	List<SimpleEvent> getRecvEventList(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type,
			@Param("eventType") EventType eventType, @Param("start") int start,
			@Param("limit") int limit);*/
	
	
	/**
	 * get receive event count
	 * @param cust_id
	 * @param service_type
	 * @param eventType
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int getRecvEventCount(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type,
			@Param("eventType") EventType eventType);
	
	/**
	 * get receive key event
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	KeyEvent getRecvKeyEvent(long id);
	
	/**
	 * get receive location event
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	LocationEvent getRecvLocationEvent(long id);
	
	/**
	 * get receive scan event
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	ScanEvent getRecvScanEvent(long id);
	
	/**
	 * get receive simple event
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	SimpleEvent getRecvSimpleEvent(long id);
	
	/**
	 * save key event
	 * @param event
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvKeyEvent(KeyEvent event);
	
	/**
	 * save location event
	 * @param event
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvLocationEvent(LocationEvent event);
	
	/**
	 * save scan event
	 * @param event
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvScanEvent(ScanEvent event);
	
	/**
	 * save simple event
	 * @param event
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvSimpleEvent(SimpleEvent event);
}
