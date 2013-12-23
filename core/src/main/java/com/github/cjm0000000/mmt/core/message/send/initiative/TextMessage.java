package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.TextNode;

/**
 * Text message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class TextMessage extends SimpleMessage {
	@JSONField(name = MsgType.TEXT)
	private TextNode textNode;
	
	public TextMessage(){
		super(MsgType.TEXT);
	}
	
	public TextMessage(String content) {
		this();
		this.textNode = new TextNode(content);
	}

	public TextNode getTextNode() {
		return textNode;
	}

	public void setTextNode(TextNode textNode) {
		this.textNode = textNode;
	}

}