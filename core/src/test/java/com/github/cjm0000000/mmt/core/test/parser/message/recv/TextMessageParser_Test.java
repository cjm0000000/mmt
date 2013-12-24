package com.github.cjm0000000.mmt.core.test.parser.message.recv;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
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
	protected void makeSpecNodesWithoutMsgId(StringBuilder sb, SimpleRecvMessage original) {
		TextMessage msg = (TextMessage) original;
		sb.append("<Content><![CDATA["+msg.getContent()+"]]></Content>");
	}

	@Override
	protected void verifySpecFieldsWithoutMsgId(SimpleRecvMessage after, SimpleRecvMessage before) {
		TextMessage t_before = (TextMessage) before;
		TextMessage t_after = (TextMessage) after;
		assertEquals(t_before.getContent(), t_after.getContent());
	}

	@Override
	protected SimpleRecvMessage getMsgInstanceWithoutMsgId() {
		TextMessage msg = new TextMessage();
		msg.setContent("中文 Hello");
		return msg;
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, TextMessage.class);
	}

}
