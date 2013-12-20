package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.github.cjm0000000.mmt.core.message.MsgType;

/**
 * Text message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class TextMessage extends SimpleMessage {
	//TODO private String content;
	
	public TextMessage() {
		super(MsgType.TEXT);
	}

}