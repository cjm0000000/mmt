package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.send.initiative.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.node.MediaNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for initiative audio message
 * @author lemon
 * @version 2.0
 *
 */
public class VoiceMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new VoiceMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		VoiceMessage msg = (VoiceMessage) original;
		String mediaId = "f121qwe324sdf86ae-0d47-455e-81f9-3f765a882efb";
		msg.setMedia(new MediaNode(mediaId));
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		VoiceMessage before = (VoiceMessage) original;
		VoiceMessage after = (VoiceMessage) MmtJSONParser.fromJSON(json, VoiceMessage.class);
		assertEquals(after.getMedia().getMediaId(), before.getMedia().getMediaId());
		return after;
	}

}
