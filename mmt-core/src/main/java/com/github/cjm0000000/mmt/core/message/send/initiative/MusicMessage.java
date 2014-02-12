package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.MusicNode;

/**
 * music message for initiative send
 * @author lemon
 * @version 2.0
 * 
 */
public class MusicMessage extends SimpleMessage {
	@JSONField(name = MsgType.MUSIC)
	private MusicNode music;

	public MusicMessage() {
		super(MsgType.MUSIC);
	}

	public MusicMessage(String title, String description, String musicUrl,
			String HQMusicUrl, String thumbMediaId) {
		this();
		this.music = new MusicNode(title, description, musicUrl, HQMusicUrl, thumbMediaId);
	}

	public MusicNode getMusic() {
		return music;
	}

	public void setMusic(MusicNode music) {
		this.music = music;
	}

}
