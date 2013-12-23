package com.github.cjm0000000.mmt.core.test.parser.message.send.passive;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;
import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;

/**
 * Unit test case for send news message
 * @author lemon
 * @version 2.0
 *
 */
public class NewsMessage_Test extends AbstractMsgTester {

	@Override
	protected void setSpecFields(SimpleMessageService original) {
		NewsMessage msg = (NewsMessage) original;
		String picUrl = "http://www.piccom/images/a.jpg";
		String url = "http://www.163.com/artic/17173.html";
		NewsNode node1 = new NewsNode("标题1", "详情1", picUrl, url);
		NewsNode node2 = new NewsNode("标题2", "详情2", picUrl, url);
		NewsNode node3 = new NewsNode("标题3", "详情3", picUrl, url);
		NewsNode node4 = new NewsNode("标题4", "详情4", picUrl, url);
		NewsNode[] news = { node1, node2, node3, node4 };
		msg.setNews(news);
	}

	@Override
	protected SimpleMessageService getMsgInstance() {
		return new NewsMessage();
	}

}
