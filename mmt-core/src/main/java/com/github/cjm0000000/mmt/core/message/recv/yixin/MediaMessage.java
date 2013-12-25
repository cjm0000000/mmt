package com.github.cjm0000000.mmt.core.message.recv.yixin;

import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;

/**
 * YiXin media message<br>
 * such as: audio, video
 * @author lemon
 * @version 1.0
 *
 */
public class MediaMessage extends SimpleRecvMessage {
	protected String url;
	protected String name;
	protected String mimeType;
	
	public MediaMessage(String msgType, String mimeType) {
		super(msgType);
		this.mimeType = mimeType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMimeType() {
		return mimeType;
	}
	protected void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
