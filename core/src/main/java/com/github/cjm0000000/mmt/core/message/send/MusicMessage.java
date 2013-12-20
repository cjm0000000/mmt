package com.github.cjm0000000.mmt.core.message.send;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Music message for send
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class MusicMessage extends SimpleMessageService {
	@MmtAlias("Music")
	private MusicNode music;
	
	public MusicMessage(){super(MsgType.MUSIC);}
	
	public MusicMessage(String title, String description, String musicUrl,
			String HQMusicUrl, String thumbMediaId){
		super(MsgType.MUSIC);
		setMusic(title, description, musicUrl, HQMusicUrl, thumbMediaId);
	}

	public MusicNode getMusic() {
		return music;
	}

	public void setMusic(String title, String description, String musicUrl,
			String HQMusicUrl, String thumbMediaId) {
		this.music = new MusicNode(title, description, musicUrl, HQMusicUrl, thumbMediaId);
	}
	
}
