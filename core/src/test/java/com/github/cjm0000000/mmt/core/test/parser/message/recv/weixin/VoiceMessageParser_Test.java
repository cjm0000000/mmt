package com.github.cjm0000000.mmt.core.test.parser.message.recv.weixin;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.MediaMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test case for voice message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class VoiceMessageParser_Test extends MediaMessageParser {

	@Override
	protected void makeSpecNodesWithoutMediaId(StringBuilder sb,
			SimpleRecvMessage original) {
		VoiceMessage msg = (VoiceMessage) original;
		sb.append("<Format><![CDATA[" + msg.getFormat()+"]]></Format>");
	}

	@Override
	protected void verifySpecFieldsWithoutMediaId(SimpleRecvMessage after, SimpleRecvMessage before) {
		VoiceMessage v_before = (VoiceMessage) before;
		VoiceMessage v_after = (VoiceMessage) after;
		assertEquals(v_after.getFormat(), v_before.getFormat());
	}

	@Override
	protected MediaMessage getMsgInstanceWithoutMediaId() {
		VoiceMessage original = new VoiceMessage();
		original.setFormat("amr");
		return original;
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, VoiceMessage.class);
	}

}
