package lemon.shared.message.metadata.specific.weixin;

import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.VoiceMessage;
import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Voice message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class WXVoiceMessage extends MediaMessage implements VoiceMessage {
	/** Format */
	@XStreamAlias("Format")
	@XStreamCDATA
	private String format;
	/** Recognition */
	@XStreamAlias("Recognition")
	@XStreamCDATA
	private String recognition;

	public WXVoiceMessage() {
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
