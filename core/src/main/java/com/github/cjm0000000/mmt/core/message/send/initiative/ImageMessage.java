package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.MediaNode;

/**
 * image message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class ImageMessage extends SimpleMessage {
	@JSONField(name = MsgType.IMAGE)
	private MediaNode media;
	
	public ImageMessage() {
		super(MsgType.IMAGE);
	}
	
	public ImageMessage(String media_id){
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
