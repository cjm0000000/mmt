package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * media node
 * @author lemon
 * @version 2.0
 *
 */
public class MediaNode {
	@JSONField(name = "media_id")
	private String mediaId;
	
	MediaNode(){}
	
	public MediaNode(String media_id){
		this.mediaId = media_id;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
