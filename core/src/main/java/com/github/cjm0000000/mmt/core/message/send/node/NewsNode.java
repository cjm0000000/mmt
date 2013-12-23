package com.github.cjm0000000.mmt.core.message.send.node;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * News node
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("item")
public class NewsNode extends VideoNode {
	@MmtCDATA
	@MmtAlias("PicUrl")
	private String picUrl;
	@MmtCDATA
	@MmtAlias("Url")
	private String url;
	
	public NewsNode(String title, String description, String picUrl, String url) {
		super(null, title, description);
		this.url = url;
		this.picUrl = picUrl;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
