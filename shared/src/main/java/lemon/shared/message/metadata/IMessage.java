package lemon.shared.message.metadata;


/**
 * 用于显示的消息类
 * @author lemon
 * @version 1.1
 *
 */
public class IMessage extends Message {
	private String createTime4display;
	private String content;
	
	public String getCreateTime4display() {
		return createTime4display;
	}
	public void setCreateTime4display(String createTime4display) {
		this.createTime4display = createTime4display;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
