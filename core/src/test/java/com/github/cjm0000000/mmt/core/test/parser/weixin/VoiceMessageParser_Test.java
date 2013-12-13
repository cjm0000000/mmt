package com.github.cjm0000000.mmt.core.test.parser.weixin;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.test.parser.AbstractMmtXMLParser;

/**
 * Unit test case for voice message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class VoiceMessageParser_Test extends AbstractMmtXMLParser {

	@Override
	protected void generateSpecXMLNodes(StringBuilder sb, Message original) {
		VoiceMessage msg = (VoiceMessage) original;
		sb.append("<Format><![CDATA[" + msg.getFormat()+"]]></Format>");
		sb.append("<MediaId><![CDATA[" + msg.getMediaId()+"]]></MediaId>");
	}

	@Override
	protected void verifySpecFields(Message after, Message before) {
		VoiceMessage v_before = (VoiceMessage) before;
		VoiceMessage v_after = (VoiceMessage) after;
		assertEquals(v_after.getMediaId(), v_before.getMediaId());
		assertEquals(v_after.getFormat(), v_before.getFormat());
	}

	@Override
	protected Message getMsgInstance() {
		VoiceMessage original = new VoiceMessage();
		original.setFormat("amr");
		original.setMediaId("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		return original;
	}

}
