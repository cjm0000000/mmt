package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;

/**
 * article bean
 * @author lemon
 * @version 2.0
 *
 */
public class Articles {
	@JSONField(name = "articles")
	private NewsNode[] news;

	Articles(){}
	
	public Articles(NewsNode[] news){
		this.news = news;
	}
	
	public NewsNode[] getNews() {
		return news;
	}

	public void setNews(NewsNode[] news) {
		this.news = news;
	}
	
}
