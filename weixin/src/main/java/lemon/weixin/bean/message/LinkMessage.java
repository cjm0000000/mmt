package lemon.weixin.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import static lemon.weixin.util.WXHelper.cDATA;

/**
 * link message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class LinkMessage extends BasicMessage {
	/** Title */
	@XStreamAlias("Title")
	private String title;
	/** Description */
	@XStreamAlias("Description")
	private String description;
	/** Url */
	@XStreamAlias("Url")
	private String url;
	
	public LinkMessage(){
		super(MsgType.LINK);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = cDATA(title);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = cDATA(description);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = cDATA(url);
	}
}
