package com.github.cjm0000000.mmt.core.test.parser.yixin;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.test.parser.AbstractMmtXMLParser;

/**
 * Unit test cases for audio message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class AudioMessageParser_Test extends AbstractMmtXMLParser {
	
	@Override
	protected void generateSpecXMLNodes(StringBuilder sb, Message original) {
		AudioMessage msg = (AudioMessage) original;
		sb.append("<url><![CDATA["+msg.getUrl()+"]]></url>");
		sb.append("<name><![CDATA["+msg.getName()+"]]></name>");
		sb.append("<mimeType><![CDATA["+msg.getMimeType()+"]]></mimeType>");
	}

	@Override
	protected void verifySpecFields(Message after, Message before) {
		AudioMessage a_before = (AudioMessage) before;
		AudioMessage a_after = (AudioMessage) after;
		assertEquals(a_after.getUrl(), a_before.getUrl());
		assertEquals(a_after.getName(), a_before.getName());
		assertEquals(a_after.getMimeType(), a_before.getMimeType());
	}

	@Override
	protected Message getMsgInstance() {
		AudioMessage original = new AudioMessage();
		original.setUrl("http://mmbiz.qpic.cn/mmbiz/QXd6JDcZQ1kNscXWUKkI4ZuLcZQQZtPIicAOB2ic5iaXKzxWytwobOXQKjiaGYFO9aO2wCGJWLyuuyhicaUqictyOibNQ/0");
		original.setName(UUID.randomUUID().toString());
		return original;
	}

}
