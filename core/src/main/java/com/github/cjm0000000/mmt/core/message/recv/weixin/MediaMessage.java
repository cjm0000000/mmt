package com.github.cjm0000000.mmt.core.message.recv.weixin;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * WeiXin media message<br>
 * such as: image, voice, video
 * @author lemon
 * @version 1.0
 *
 */
public class MediaMessage extends Message {
	/** MediaId */
	@MmtAlias("MediaId")
	protected String mediaId;
	
	public MediaMessage(String msgType) {
		super(msgType);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
