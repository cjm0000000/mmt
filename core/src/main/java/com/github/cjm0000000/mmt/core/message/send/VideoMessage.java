package com.github.cjm0000000.mmt.core.message.send;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Video message for send
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class VideoMessage extends SimpleMessageService {
	@MmtAlias("Video")
	private VideoNode video;
	
	public VideoMessage(){super(MsgType.VIDEO);}
	
	public VideoMessage(String mediaId, String title, String description){
		super(MsgType.VIDEO);
		setVideo(mediaId, title, description);
	}

	public VideoNode getVideo() {
		return video;
	}

	public void setVideo(String mediaId, String title, String description) {
		this.video = new VideoNode(mediaId, title, description);
	}
}
