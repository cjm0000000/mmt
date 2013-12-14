package com.github.cjm0000000.mmt.core.test.parser.event;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import com.github.cjm0000000.mmt.core.EventType;
import com.github.cjm0000000.mmt.core.event.KeyEvent;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;

/**
 * Unit test case for key event
 * @author lemon
 * @version 2.0
 *
 */
public class KeyEventParser_Test extends AbstractEventParser {

	@Override
	protected void makeSpecNodesWithoutEventType(StringBuilder sb,
			SimpleEvent original) {
		KeyEvent event = (KeyEvent) original;
		sb.append("<EventKey><![CDATA[" + event.getEventKey() + "]]></EventKey>");
	}

	@Override
	protected void verifySpecFieldsWithoutEventType(SimpleEvent after,
			SimpleEvent before) {
		KeyEvent k_before = (KeyEvent) before;
		KeyEvent k_after = (KeyEvent) after;
		assertEquals(k_after.getEventKey(), k_before.getEventKey());
	}

	@Override
	protected SimpleEvent getEventInstance() {
		KeyEvent event = new KeyEvent();
		event.setEventType(EventType.CLICK);
		event.setEventKey(UUID.randomUUID().toString());
		return event;
	}

}
