package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Text message for receive
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@MmtAlias("xml")
public class TextMessage extends SimpleRecvMessage {
	/** Content */
	@MmtCDATA
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
