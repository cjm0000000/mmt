package com.github.cjm0000000.mmt.core.test.parser.event;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.EventType;
import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.test.parser.AbstractXMLParser;

/**
 * Unit test cases
 * @author lemon
 *
 */
public class SimpleEventParser_Test extends AbstractXMLParser {

	@Override
	protected void makeSpecNodes(StringBuilder sb, SimpleMessageService original) {
		SimpleEvent event = (SimpleEvent) original;
		sb.append("<Event><![CDATA[" + event.getEventType() + "]]></Event>");
	}

	@Override
	protected void verifySpecFields(SimpleMessageService after,
			SimpleMessageService before) {
		SimpleEvent s_before = (SimpleEvent) before;
		SimpleEvent s_after = (SimpleEvent) after;
		assertEquals(s_after.getEventType(), s_before.getEventType());
	}

	@Override
	protected SimpleMessageService getMsgInstance() {
		SimpleEvent original = new SimpleEvent();
		original.setEventType(EventType.subscribe.toString());
		return original;
	}

}
