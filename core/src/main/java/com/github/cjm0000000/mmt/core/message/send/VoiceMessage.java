package com.github.cjm0000000.mmt.core.message.send;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Voice message for send
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class VoiceMessage extends SimpleMessageService{
	@MmtAlias("Voice")
	private MediaNode voice;
	
	public VoiceMessage(){super(MsgType.VOICE);}
	
	public VoiceMessage(String mediaId){
		super(MsgType.VOICE);
		setVoice(mediaId);
	}

	public MediaNode getVoice() {
		return voice;
	}

	public void setVoice(String mediaId) {
		this.voice = new MediaNode(mediaId);
	}
	
}
