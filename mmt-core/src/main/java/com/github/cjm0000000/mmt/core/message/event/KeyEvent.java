package com.github.cjm0000000.mmt.core.message.event;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * event with key
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class KeyEvent extends SimpleEvent {
	@MmtCDATA
	@MmtAlias("EventKey")
	protected String eventKey;
	
	public KeyEvent(){
		super(EventType.CLICK);
	}

	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
