package com.github.cjm0000000.mmt.core.message.recv.yixin;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Voice message
 * @author lemon
 * @version 1.0
 * 
 */
@MmtAlias("xml")
public class AudioMessage extends MediaMessage {
	public AudioMessage() {
		super(MsgType.AUDIO, "audio/aac");
	}
}
