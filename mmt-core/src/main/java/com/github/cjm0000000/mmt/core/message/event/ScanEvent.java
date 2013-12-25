package com.github.cjm0000000.mmt.core.message.event;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Scan event
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public final class ScanEvent extends KeyEvent {
	@MmtCDATA
	@MmtAlias("Ticket")
	private String ticket;

	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
