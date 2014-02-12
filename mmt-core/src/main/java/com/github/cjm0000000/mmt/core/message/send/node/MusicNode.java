package com.github.cjm0000000.mmt.core.message.send.node;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Music node
 * @author lemon
 * @version 2.0
 *
 */
public class MusicNode extends VideoNode{
	@MmtCDATA
	@MmtAlias("MusicUrl")
	@JSONField(name = "musicurl")
	private String musicUrl;
	@MmtCDATA
	@MmtAlias("HQMusicUrl")
	@JSONField(name = "hqmusicurl")
	private String hqMusicUrl;
	@MmtCDATA
	@MmtAlias("ThumbMediaId")
	@JSONField(name = "thumb_media_id")
	private String thumbMediaId;
	
	MusicNode(){}
	
	public MusicNode(String title, String description, String musicUrl,
			String hqMusicUrl, String thumbMediaId) {
		super(null, title, description);
		this.musicUrl = musicUrl;
		this.hqMusicUrl = hqMusicUrl;
		this.thumbMediaId = thumbMediaId;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
}
