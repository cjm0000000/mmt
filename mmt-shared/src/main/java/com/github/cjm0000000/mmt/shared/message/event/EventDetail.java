package com.github.cjm0000000.mmt.shared.message.event;

import com.github.cjm0000000.mmt.core.message.event.LocationEvent;

/**
 * event detail for log
 * @author lemon
 * @version 2.0
 *
 */
public class EventDetail extends LocationEvent {
	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
