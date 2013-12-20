package com.github.cjm0000000.mmt.core.message.send.passive;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Media node for send
 * @author lemon
 * @version 2.0
 *
 */
public class MediaNode {
	/** MediaId */
	@MmtCDATA
	@MmtAlias("MediaId")
	protected String mediaId;
	
	MediaNode(String mediaId){
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
