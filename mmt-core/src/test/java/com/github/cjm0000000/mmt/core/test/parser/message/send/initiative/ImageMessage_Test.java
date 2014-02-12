package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.send.initiative.ImageMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.node.MediaNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for initiative image message
 * @author lemon
 * @version 2.0
 *
 */
public class ImageMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new ImageMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		ImageMessage msg = (ImageMessage) original;
		String mediaId = "f12186ae-0d47-455e-81f9-3f765a882efb";
		msg.setMedia(new MediaNode(mediaId));
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		ImageMessage before = (ImageMessage) original;
		ImageMessage after = (ImageMessage) MmtJSONParser.fromJSON(json, ImageMessage.class);
		assertEquals(after.getMedia().getMediaId(), before.getMedia().getMediaId());
		return after;
	}

}
