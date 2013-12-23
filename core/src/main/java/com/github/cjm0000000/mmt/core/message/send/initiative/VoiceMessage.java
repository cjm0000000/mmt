package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.MediaNode;

/**
 * audio message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class VoiceMessage extends SimpleMessage {
	@JSONField(name = MsgType.VOICE)
	private MediaNode media;

	public VoiceMessage() {
		super(MsgType.VOICE);
	}
	
	public VoiceMessage(String media_id){
		this();
		this.media = new MediaNode(media_id);
	}

	public MediaNode getMedia() {
		return media;
	}

	public void setMedia(MediaNode media) {
		this.media = media;
	}

}
