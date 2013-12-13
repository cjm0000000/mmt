package com.github.cjm0000000.mmt.core.test.parser.weixin;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.weixin.MediaMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;

/**
 * Unit test case for voice message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class VoiceMessageParser_Test extends MediaMessageParser {

	@Override
	protected void generateSpecXMLNodesWithoutMediaId(StringBuilder sb,
			Message original) {
		VoiceMessage msg = (VoiceMessage) original;
		sb.append("<Format><![CDATA[" + msg.getFormat()+"]]></Format>");
	}

	@Override
	protected void verifySpecFieldsWithoutMediaId(Message after, Message before) {
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

}
