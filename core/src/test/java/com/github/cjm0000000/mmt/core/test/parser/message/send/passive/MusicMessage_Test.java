package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.send.passive.MusicMessage;

/**
 * Unit test case for send music message
 * @author lemon
 * @version 2.0
 *
 */
public class MusicMessage_Test extends AbstractMsgTester {

	@Override
	protected void setSpecFields(SimpleMessageService original) {
		MusicMessage msg = (MusicMessage) original;
		final String title = "吻别——张学友";
		final String description = "这是一首老歌";
		final String musicUrl = "http://www.baidu.com/music/a.mp3";
		final String HQMusicUrl = "http://www.baidu.com/hqmusic/a.ape";
		final String thumbMediaId = "1@)#*!)$*isdfoejoehgfo@JONEO@HOR";
		msg.setMusic(title, description, musicUrl, HQMusicUrl, thumbMediaId);
	}

	@Override
	protected SimpleMessageService getMsgInstance() {
		return new MusicMessage();
	}

}
