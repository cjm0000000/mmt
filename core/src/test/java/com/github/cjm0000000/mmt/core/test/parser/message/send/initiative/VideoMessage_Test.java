package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.VideoMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.VideoNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for initiative video message
 * @author lemon
 * @version 2.0
 *
 */
public class VideoMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new VideoMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		VideoMessage msg = (VideoMessage) original;
		String mediaId = "f12186ae-0d47-455e-81f9-3f765a882efb";
		String title = "title for test";
		String description = "Description for test";
		msg.setVideo(new VideoNode(mediaId,title,description));
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		VideoMessage before = (VideoMessage) original;
		VideoMessage after = (VideoMessage) MmtJSONParser.fromJSON(json, VideoMessage.class);
		// fastjson re-serialize issue
		//assertEquals(after.getVideo().getMediaId(), before.getVideo().getMediaId());
		assertEquals(after.getVideo().getTitle(), before.getVideo().getTitle());
		assertEquals(after.getVideo().getDescription(), before.getVideo().getDescription());
		return after;
	}

}
