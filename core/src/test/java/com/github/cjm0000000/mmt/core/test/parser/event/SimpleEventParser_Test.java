package com.github.cjm0000000.mmt.core.test.parser.event;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.event.EventType;
import com.github.cjm0000000.mmt.core.message.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test cases
 * @author lemon
 * @version 2.0
 *
 */
public class SimpleEventParser_Test extends AbstractEventParser {

	@Override
	protected void makeSpecNodesWithoutEventType(StringBuilder sb,
			SimpleEvent original) {
	}

	@Override
	protected SimpleEvent getEventInstance() {
		SimpleEvent event = new SimpleEvent();
		event.setEventType(EventType.subscribe);
		return event;
	}

	@Override
	protected void verifySpecFieldsWithoutEventType(SimpleEvent after,
			SimpleEvent before) {
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, SimpleEvent.class);
	}

}
