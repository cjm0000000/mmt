package com.github.cjm0000000.mmt.core.event;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * event with key
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class KeyEvent extends SimpleEvent {
	@MmtAlias("EventKey")
	protected String eventKey;

	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
