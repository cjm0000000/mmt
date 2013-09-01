package lemon.weixin.bean.message;

import static lemon.weixin.util.WXHelper.cDATA;

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
	private String thumbMediaId;
	
	public VideoMessage() {
		super(MsgType.VIDEO);
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = cDATA(thumbMediaId);
	}

}
