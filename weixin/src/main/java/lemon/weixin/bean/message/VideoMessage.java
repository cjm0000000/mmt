package lemon.weixin.bean.message;

import lemon.shared.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * video message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class VideoMessage extends MediaMessage {
	/** ThumbMediaId */
	@XStreamAlias("ThumbMediaId")
	@XStreamCDATA
	private String thumbMediaId;
	
	public VideoMessage() {
		super(MsgType.VIDEO);
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}
