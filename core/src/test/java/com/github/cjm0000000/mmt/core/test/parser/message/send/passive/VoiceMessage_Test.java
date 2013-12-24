package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.send.passive.VoiceMessage;

/**
 * Unit test case for send voice message
 * @author lemon
 * @version 2.0
 *
 */
public class VoiceMessage_Test extends AbstractMsgTester {

	@Override
	protected void setSpecFields(BaseMessage original) {
		VoiceMessage msg = (VoiceMessage) original;
		msg.setVoice("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
	}

	@Override
	protected BaseMessage getMsgInstance() {
		return new VoiceMessage();
	}
	
}
