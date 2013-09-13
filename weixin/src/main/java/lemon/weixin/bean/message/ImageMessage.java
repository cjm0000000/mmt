package lemon.weixin.bean.message;

import lemon.shared.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Image message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
public class ImageMessage extends MediaMessage {
	/** PicUrl */
	@XStreamAlias("PicUrl")
	@XStreamCDATA
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
