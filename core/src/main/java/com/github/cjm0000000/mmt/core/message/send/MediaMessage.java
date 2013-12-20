package com.github.cjm0000000.mmt.core.message.send;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Simple media message for send
 * @author lemon
 * @version 2.0
 *
 */
public class MediaMessage {
	/** MediaId */
	@MmtCDATA
	@MmtAlias("MediaId")
	protected String mediaId;
	
	MediaMessage(String mediaId){
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
