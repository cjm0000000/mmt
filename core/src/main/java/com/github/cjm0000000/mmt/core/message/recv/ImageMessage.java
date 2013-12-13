package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.recv.weixin.MediaMessage;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Image message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class ImageMessage extends MediaMessage {
	/** PicUrl */
	@MmtAlias("PicUrl")
	private String picUrl;

	public ImageMessage(){
		super(MsgType.IMAGE);
	}
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
