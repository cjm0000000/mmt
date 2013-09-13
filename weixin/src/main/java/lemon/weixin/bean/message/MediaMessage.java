package lemon.weixin.bean.message;

import lemon.shared.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * media message<br>
 * such as: image, voice, video
 * @author lemon
 *
 */
public class MediaMessage extends WeiXinMessage {
	/** MediaId */
	@XStreamAlias("MediaId")
	@XStreamCDATA
	protected String mediaId;
	
	public MediaMessage(String msgType) {
		super(msgType);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
