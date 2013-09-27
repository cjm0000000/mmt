package lemon.weixin.bean.message;

import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * video message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
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
