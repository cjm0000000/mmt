package com.github.cjm0000000.mmt.core.message.recv.weixin;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * video message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class VideoMessage extends MediaMessage {
	/** ThumbMediaId */
	@MmtCDATA
	@MmtAlias("ThumbMediaId")
	private String thumbMediaId;
	
	public VideoMessage() {
		super(MsgType.VIDEO);
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}
