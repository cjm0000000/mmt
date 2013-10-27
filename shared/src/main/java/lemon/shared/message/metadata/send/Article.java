package lemon.shared.message.metadata.send;

import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Article for news message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamAlias("item")
@XStreamProcessCDATA
public class Article {
	/** Title */
	@XStreamAlias("Title")
	@XStreamCDATA
	private String title;
	/** Description */
	@XStreamAlias("Description")
	@XStreamCDATA
	private String description;
	/** PicUrl(图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80) */
	@XStreamAlias("PicUrl")
	@XStreamCDATA
	private String picUrl;
	/** Url(点击图文消息跳转链接) */
	@XStreamAlias("Url")
	@XStreamCDATA
	private String url;
	/** Article's ID */
	@XStreamOmitField
	private int id;

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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
