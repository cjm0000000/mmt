package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;

/**
 * news message for initiative send
 * @author lemon
 * @version 2.0
 *
 */
public class NewsMessage extends SimpleMessage {
	@JSONField(name = MsgType.NEWS)
	private Articles articles;
	
	public NewsMessage() {
		super(MsgType.NEWS);
	}
	
	public NewsMessage(NewsNode[] news) {
		this();
		this.articles = new Articles(news);
	}

	public Articles getArticles() {
		return articles;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
	}

}
