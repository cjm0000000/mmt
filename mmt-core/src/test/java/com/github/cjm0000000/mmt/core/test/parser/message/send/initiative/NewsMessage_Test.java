package com.github.cjm0000000.mmt.core.test.parser.message.send.initiative;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.message.send.initiative.Articles;
import com.github.cjm0000000.mmt.core.message.send.initiative.NewsMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.SimpleMessage;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;
import com.github.cjm0000000.mmt.core.parser.MmtJSONParser;

/**
 * Unit test case for send news message
 * @author lemon
 * @version 2.0
 *
 */
public class NewsMessage_Test extends AbstractMsgTester {

	@Override
	protected SimpleMessage getMsgInstance() {
		return new NewsMessage();
	}

	@Override
	protected void setSpecFields(SimpleMessage original) {
		NewsMessage msg = (NewsMessage) original;
		String picUrl = "http://www.piccom/images/a.jpg";
		String url = "http://www.163.com/artic/17173.html";
		NewsNode node1 = new NewsNode("标题1", "详情1", picUrl, url);
		NewsNode node2 = new NewsNode("标题2", "详情2", picUrl, url);
		NewsNode node3 = new NewsNode("标题3", "详情3", picUrl, url);
		NewsNode node4 = new NewsNode("标题4", "详情4", picUrl, url);
		NewsNode[] news = { node1, node2, node3, node4 };
		msg.setArticles(new Articles(news));
		
	}

	@Override
	protected SimpleMessage verifySpecFields(SimpleMessage original, String json) {
		NewsMessage before = (NewsMessage) original;
		NewsMessage after = (NewsMessage) MmtJSONParser.fromJSON(json, NewsMessage.class);
		assertEquals(after.getArticles().getNews().length, before.getArticles().getNews().length);
		return after;
	}


}
