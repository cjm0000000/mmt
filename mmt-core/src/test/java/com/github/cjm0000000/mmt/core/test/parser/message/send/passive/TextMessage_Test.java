package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.send.passive.TextMessage;

/**
 * Unit test case for send text message
 * @author lemon
 * @version 2.0
 *
 */
public class TextMessage_Test extends AbstractMsgTester{

	@Override
	protected void setSpecFields(BaseMessage original) {
		TextMessage msg = (TextMessage) original;
		msg.setContent("测试中文。Test EN");
	}

	@Override
	protected BaseMessage getMsgInstance() {
		return new TextMessage();
	}

}
