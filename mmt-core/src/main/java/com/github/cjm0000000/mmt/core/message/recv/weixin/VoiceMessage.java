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

	public VoiceMessage() {
		super(MsgType.VOICE);
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

}
