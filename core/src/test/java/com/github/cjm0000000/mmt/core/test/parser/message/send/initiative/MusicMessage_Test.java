package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import com.github.cjm0000000.mmt.core.message.send.initiative.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.node.MusicNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for initiative video message
 * @author lemon
 * @version 2.0
 *
 */
public class MusicMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new MusicMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		MusicMessage msg = (MusicMessage) original;
		String title = "title for test";
		String description = "Description for test";
		String musicUrl = "http://music.baidu.com/mp3/aaa.mp3";
		String HQMusicUrl = "http://music.baidu.com/hq/a.ape";
		String thumbMediaId = UUID.randomUUID().toString();
		msg.setMusic(new MusicNode(title, description, musicUrl, HQMusicUrl, thumbMediaId));
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		MusicMessage before = (MusicMessage) original;
		MusicMessage after = (MusicMessage) MmtJSONParser.fromJSON(json, MusicMessage.class);
		assertEquals(after.getMusic().getTitle(), before.getMusic().getTitle());
		assertEquals(after.getMusic().getDescription(), before.getMusic().getDescription());
		assertEquals(after.getMusic().getMusicUrl(), before.getMusic().getMusicUrl());
		assertEquals(after.getMusic().getHqMusicUrl(), before.getMusic().getHqMusicUrl());
		assertEquals(after.getMusic().getThumbMediaId(), before.getMusic().getThumbMediaId());
		return after;
	}

}
