package lemon.weixin.bean.message;

import lemon.weixin.xstream.annotations.XStreamCDATA;

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
	@XStreamCDATA
	private String format;
	/** Recognition */
	@XStreamAlias("Recognition")
	@XStreamCDATA
	private String recognition;

	public VoiceMessage() {
		super(MsgType.VOICE);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}
