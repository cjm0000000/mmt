package com.github.cjm0000000.mmt.core.message.recv.yixin;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * video message
 * @author lemon
 * @version 1.0
 * 
 */
@MmtAlias("xml")
public class VideoMessage extends MediaMessage implements IVideo {
	public VideoMessage() {
		super(MsgType.VIDEO, "video/mp4");
	}

}
