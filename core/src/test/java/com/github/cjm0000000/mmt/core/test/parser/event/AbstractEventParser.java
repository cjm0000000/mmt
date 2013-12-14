package com.github.cjm0000000.mmt.core.test.parser.event;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;
import com.github.cjm0000000.mmt.core.test.parser.AbstractXMLParser;

/**
 * Event test template
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractEventParser extends AbstractXMLParser {
	
	/**
	 * make specific nodes without event type
	 * @param sb
	 * @param original
	 */
	protected abstract void makeSpecNodesWithoutEventType(StringBuilder sb,
			SimpleEvent original);
	
	/**
	 * Get event instance
	 * @return
	 */
	protected abstract SimpleEvent getEventInstance();
	
	/**
	 * Verify specific fields without event type
	 * @param after
	 * @param before
	 */
	protected abstract void verifySpecFieldsWithoutEventType(SimpleEvent after, SimpleEvent before);

	@Override
	protected final void makeSpecNodes(StringBuilder sb,
			SimpleMessageService original) {
		SimpleEvent msg = (SimpleEvent) original;
		sb.append("<Event><![CDATA[" + msg.getEventType() + "]]></Event>");
		makeSpecNodesWithoutEventType(sb, msg);
	}

	@Override
	protected final void verifySpecFields(SimpleMessageService after, SimpleMessageService before) {
		SimpleEvent s_before = (SimpleEvent) before;
		SimpleEvent s_after = (SimpleEvent) after;
		assertEquals(s_after.getEventType(), s_before.getEventType());
		verifySpecFieldsWithoutEventType(s_after, s_before);
	}

	@Override
	protected final SimpleMessageService getMsgInstance() {
		return getEventInstance();
	}

}
