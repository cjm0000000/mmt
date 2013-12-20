package com.github.cjm0000000.mmt.core.message.send;

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
	private String musicUrl;
	@MmtCDATA
	@MmtAlias("HQMusicUrl")
	private String HQMusicUrl;
	@MmtCDATA
	@MmtAlias("ThumbMediaId")
	private String thumbMediaId;
	
	MusicNode(String title, String description, String musicUrl,
			String HQMusicUrl, String thumbMediaId) {
		super(null, title, description);
		this.musicUrl = musicUrl;
		this.HQMusicUrl = HQMusicUrl;
		this.thumbMediaId = thumbMediaId;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		this.HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
}
