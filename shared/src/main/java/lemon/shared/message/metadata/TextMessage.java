package lemon.shared.message.metadata;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Text message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class TextMessage extends Message {
	/** Content */
	@XStreamAlias("Content")
	@XStreamCDATA
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
