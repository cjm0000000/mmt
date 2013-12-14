package com.github.cjm0000000.mmt.core.event;

import com.github.cjm0000000.mmt.core.EventType;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * simple event
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class SimpleEvent extends SimpleMessageService {
	/** Event */
	@MmtAlias("Event")
	private EventType eventType; 
	
	public SimpleEvent(){
		super(MsgType.EVENT);
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
