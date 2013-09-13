package lemon.weixin.bean.message;

import lemon.shared.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * link message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class LinkMessage extends WeiXinMessage {
	/** Title */
	@XStreamAlias("Title")
	@XStreamCDATA
	private String title;
	/** Description */
	@XStreamAlias("Description")
	@XStreamCDATA
	private String description;
	/** Url */
	@XStreamAlias("Url")
	@XStreamCDATA
	private String url;
	
	public LinkMessage(){
		super(MsgType.LINK);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
