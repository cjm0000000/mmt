package com.github.cjm0000000.mmt.core.message.event;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * simple event
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class SimpleEvent extends BaseMessage {
	/** Event */
	@MmtCDATA
	@MmtAlias("Event")
	private EventType eventType; 
	
	public SimpleEvent(){
		super(MsgType.EVENT);
	}
	
	public SimpleEvent(EventType eventType){
		this();
		this.eventType = eventType;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
