package com.github.cjm0000000.mmt.core.test.parser.message.send;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.send.TextMessage;

/**
 * Unit test case for send text message
 * @author lemon
 * @version 2.0
 *
 */
public class TextMessage_Test extends AbstractMsgTester{

	@Override
	protected void setSpecFields(SimpleMessageService msg) {
		TextMessage m = (TextMessage) msg;
		m.setContent("测试中文。Test EN");
	}

}
