package com.github.cjm0000000.mmt.core.test.parser.event;

import com.github.cjm0000000.mmt.core.EventType;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;

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

}
