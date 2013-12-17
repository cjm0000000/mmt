package com.github.cjm0000000.mmt.core.test.parser.message;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test case for text message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class TextMessageParser_Test extends AbstractMsgParser {
	
	@Override
	protected void makeSpecNodesWithoutMsgId(StringBuilder sb, Message original) {
		TextMessage msg = (TextMessage) original;
		sb.append("<Content><![CDATA["+msg.getContent()+"]]></Content>");
	}

	@Override
	protected void verifySpecFieldsWithoutMsgId(Message after, Message before) {
		TextMessage t_before = (TextMessage) before;
		TextMessage t_after = (TextMessage) after;
		assertEquals(t_before.getContent(), t_after.getContent());
	}

	@Override
	protected Message getMsgInstanceWithoutMsgId() {
		TextMessage msg = new TextMessage();
		msg.setContent("中文 Hello");
		return msg;
	}

	@Override
	protected SimpleMessageService fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, TextMessage.class);
	}

}
