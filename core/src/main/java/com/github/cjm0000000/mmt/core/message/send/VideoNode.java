package com.github.cjm0000000.mmt.core.message.send;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Video node for send
 * @author lemon
 * @version 2.0
 *
 */
public class VideoNode extends MediaNode {
	@MmtCDATA
	@MmtAlias("Title")
	private String title;
	@MmtCDATA
	@MmtAlias("Description")
	private String description;
	
	VideoNode(String mediaId, String title, String description) {
		super(mediaId);
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
