package lemon.weixin.bean.message;

import static lemon.weixin.util.WXHelper.cDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Voice message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class VoiceMessage extends MediaMessage {
	/** Format */
	@XStreamAlias("Format")
	private String format;
	/** Recognition */
	@XStreamAlias("Recognition")
	private String recognition;

	public VoiceMessage() {
		super(MsgType.VOICE);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = cDATA(format);
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = cDATA(recognition);
	}
	
}
