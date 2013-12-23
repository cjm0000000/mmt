package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.send.passive.VideoMessage;

/**
 * Unit test case for send video message
 * @author lemon
 * @version 2.0
 *
 */
public class VideoMessage_Test extends AbstractMsgTester {

	@Override
	protected void setSpecFields(SimpleMessageService original) {

	}

	@Override
	protected SimpleMessageService getMsgInstance() {
		String mediaId = "Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl";
		return new VideoMessage(mediaId, "标题", "Desc");
	}

}
