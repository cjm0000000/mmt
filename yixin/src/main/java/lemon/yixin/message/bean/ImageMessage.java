package lemon.yixin.message.bean;

import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Image message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class ImageMessage extends YiXinMessage {
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
