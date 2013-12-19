package com.github.cjm0000000.mmt.core.test.parser.message.yixin;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.UUID;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.test.parser.message.AbstractMsgParser;

/**
 * Unit test cases for audio message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class AudioMessageParser_Test extends AbstractMsgParser {
	
	@Override
	protected void makeSpecNodesWithoutMsgId(StringBuilder sb, SimpleRecvMessage original) {
		AudioMessage msg = (AudioMessage) original;
		sb.append("<url><![CDATA["+msg.getUrl()+"]]></url>");
		sb.append("<name><![CDATA["+msg.getName()+"]]></name>");
		sb.append("<mimeType><![CDATA["+msg.getMimeType()+"]]></mimeType>");
	}

	@Override
	protected void verifySpecFieldsWithoutMsgId(SimpleRecvMessage after, SimpleRecvMessage before) {
		AudioMessage a_before = (AudioMessage) before;
		AudioMessage a_after = (AudioMessage) after;
		assertEquals(a_after.getUrl(), a_before.getUrl());
		assertEquals(a_after.getName(), a_before.getName());
		assertEquals(a_after.getMimeType(), a_before.getMimeType());
	}

	@Override
	protected SimpleRecvMessage getMsgInstanceWithoutMsgId() {
		AudioMessage original = new AudioMessage();
		original.setUrl("http://mmbiz.qpic.cn/mmbiz/QXd6JDcZQ1kNscXWUKkI4ZuLcZQQZtPIicAOB2ic5iaXKzxWytwobOXQKjiaGYFO9aO2wCGJWLyuuyhicaUqictyOibNQ/0");
		original.setName(UUID.randomUUID().toString());
		return original;
	}

	@Override
	protected SimpleMessageService fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, AudioMessage.class);
	}

}
