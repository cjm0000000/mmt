package com.github.cjm0000000.mmt.core.message.recv.weixin;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Voice message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class VoiceMessage extends MediaMessage {
	/** Format */
	@MmtCDATA
	@MmtAlias("Format")
	private String format;
	/** 对于开通语音识别的账户，这里存放的是语音识别的结果 */
	@MmtCDATA
	@MmtAlias("Recognition")
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
