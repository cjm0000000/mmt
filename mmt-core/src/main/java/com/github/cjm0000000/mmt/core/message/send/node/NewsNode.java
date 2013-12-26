package com.github.cjm0000000.mmt.core.message.send.node;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtOmitField;

/**
 * News node
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("item")
public class NewsNode extends VideoNode {
	@MmtOmitField
	private long id;
	@MmtCDATA
	@MmtAlias("PicUrl")
	@JSONField(name = "picurl")
	private String picUrl;
	@MmtCDATA
	@MmtAlias("Url")
	@JSONField(name = "url")
	private String url;
	
	public NewsNode(){}
	
	public NewsNode(String title, String description, String picUrl, String url) {
		super(null, title, description);
		this.url = url;
		this.picUrl = picUrl;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
