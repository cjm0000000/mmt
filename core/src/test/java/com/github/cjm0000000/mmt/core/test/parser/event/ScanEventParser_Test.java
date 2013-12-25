package com.github.cjm0000000.mmt.core.test.parser.event;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.UUID;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.ScanEvent;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test case for scan event
 * @author lemon
 * @version 2.0
 *
 */
public class ScanEventParser_Test extends AbstractEventParser {

	@Override
	protected void makeSpecNodesWithoutEventType(StringBuilder sb,
			SimpleEvent original) {
		ScanEvent event = (ScanEvent) original;
		sb.append("<EventKey><![CDATA[" + event.getEventKey() + "]]></EventKey>");
		sb.append("<Ticket><![CDATA[" + event.getTicket() + "]]></Ticket>");
	}

	@Override
	protected SimpleEvent getEventInstance() {
		ScanEvent event = new ScanEvent();
		event.setEventType(EventType.scan);
		event.setEventKey(UUID.randomUUID().toString());
		event.setTicket(UUID.randomUUID().toString());
		return event;
	}

	@Override
	protected void verifySpecFieldsWithoutEventType(SimpleEvent after,
			SimpleEvent before) {
		ScanEvent l_before = (ScanEvent) before;
		ScanEvent l_after = (ScanEvent) after;
		assertEquals(l_after.getEventKey(), l_before.getEventKey());
		assertEquals(l_after.getTicket(), l_before.getTicket());
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is);
	}

}
