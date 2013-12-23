package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.TextMessage;
import com.github.cjm0000000.mmt.core.message.send.node.TextNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for initiative send text message
 * @author lemon
 * @version 2.0
 *
 */
public class TextMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new TextMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		TextMessage msg = (TextMessage) original;
		msg.setTextNode(new TextNode("张  Lemon"));
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		TextMessage before = (TextMessage) original;
		TextMessage after = (TextMessage) MmtJSONParser.fromJSON(json, TextMessage.class);
		assertEquals(before.getTextNode().getContent(), after.getTextNode().getContent());
		return after;
	}
	
}
