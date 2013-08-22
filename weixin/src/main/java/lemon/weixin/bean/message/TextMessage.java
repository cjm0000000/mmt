package lemon.weixin.bean.message;

import static lemon.weixin.util.WXHelper.cDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Text message
 * 
 * @author lemon
 * 
 */
@XStreamAlias("xml")
public class TextMessage extends BasicMessage {
	/** Content */
	@XStreamAlias("Content")
	private String content;

	public TextMessage(){
		super(MsgType.TEXT);
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = cDATA(content);
	}
}
