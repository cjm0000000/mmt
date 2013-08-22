package lemon.weixin.bean.message;

import lemon.weixin.util.WXHelper;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Image message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class ImageMessage extends BasicMessage {
	/** PicUrl */
	@XStreamAlias("PicUrl")
	private String picUrl;

	public ImageMessage(){
		super(MsgType.IMAGE);
	}
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = WXHelper.cDATA(picUrl);
	}
}
