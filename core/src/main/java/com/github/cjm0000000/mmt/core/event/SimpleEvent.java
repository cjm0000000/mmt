package com.github.cjm0000000.mmt.core.event;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Event message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class SimpleEvent extends SimpleMessageService {
	/** Event */
	@MmtAlias("Event")
	private String eventType; 
	
	public SimpleEvent(){
		super(MsgType.EVENT);
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
