package lemon.shared.message.metadata.specific.weixin;

import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.VideoMessage;
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
public class WXVideoMessage extends MediaMessage implements VideoMessage {
	/** ThumbMediaId */
	@XStreamAlias("ThumbMediaId")
	@XStreamCDATA
	private String thumbMediaId;
	
	public WXVideoMessage() {
		super(MsgType.VIDEO);
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}
