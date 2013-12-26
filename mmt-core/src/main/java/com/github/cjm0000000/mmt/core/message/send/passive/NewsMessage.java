package com.github.cjm0000000.mmt.core.message.send.passive;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * News message for send
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class NewsMessage extends BaseMessage {
	@MmtAlias("Articles")
	private NewsNode[] news;
	@MmtAlias("ArticleCount")
	private int articleCount;
	public NewsMessage() { super(MsgType.NEWS); }
	
	public NewsMessage(String title, String description, String picUrl, String url) {
		this();
		this.news = new NewsNode[]{new NewsNode(title, description, picUrl, url)};
		this.articleCount = news.length;
	}

	public NewsNode[] getNews() {
		return news;
	}
	public void setNews(NewsNode[] news) {
		this.news = news;
		this.articleCount = news.length;
	}
	public int getArticleCount() {
		return news.length;
	}
	
}
