package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.VideoNode;

/**
 * video message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class VideoMessage extends SimpleMessage {
	@JSONField(name = MsgType.VIDEO)
	private VideoNode video;
	
	public VideoMessage() {
		super(MsgType.VIDEO);
	}
	
	public VideoMessage(String media_id, String title, String description){
		this();
		this.video = new VideoNode(media_id, title, description);
	}

	public VideoNode getVideo() {
		return video;
	}

	public void setVideo(VideoNode video) {
		this.video = video;
	}

}
