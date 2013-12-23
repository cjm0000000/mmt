package com.github.cjm0000000.mmt.core.message.send.initiative;

/**
 * Text node
 * @author lemon
 * @version 2.0
 *
 */
public class TextNode {
	private String content;
	
	public TextNode(){}
	
	public TextNode(String content){
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
