package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Text message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@MmtAlias("xml")
public class TextMessage extends Message {
	/** Content */
	@MmtAlias("Content")
	private String content;

	public TextMessage(){
		super(MsgType.TEXT);
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
