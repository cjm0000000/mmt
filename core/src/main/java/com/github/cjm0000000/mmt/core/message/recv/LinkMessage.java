package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * link message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class LinkMessage extends Message {
	/** Title */
	@MmtAlias("Title")
	private String title;
	/** Description */
	@MmtAlias("Description")
	private String description;
	/** Url */
	@MmtAlias("Url")
	private String url;
	
	public LinkMessage(){
		super(MsgType.LINK);
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
