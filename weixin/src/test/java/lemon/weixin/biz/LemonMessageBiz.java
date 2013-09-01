package lemon.weixin.biz;

import lemon.weixin.bean.message.Article;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.biz.customer.CustBasicMsgProcessor;
import lemon.weixin.biz.parser.MusicMsgParser;
import lemon.weixin.biz.parser.NewsMsgParser;
import lemon.weixin.biz.parser.TextMsgParser;
import lemon.weixin.biz.parser.VideoMsgParser;
import lemon.weixin.biz.parser.VoiceMsgParser;

/**
 * Lemon message test business
 * @author lemon
 *
 */
public class LemonMessageBiz extends CustBasicMsgProcessor {

	@Override
	public String processImageMsg(String mmt_token, ImageMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Image message replay.");
		return new TextMsgParser().toXML(replyMsg);
	}

	@Override
	public String processLinkMsg(String token, LinkMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Link message replay.");
		return new TextMsgParser().toXML(replyMsg);
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Location message replay.");
		return new TextMsgParser().toXML(replyMsg);
	}

	@Override
	public String processMusicMsg(String token, MusicMessage msg) {
		MusicMessage replyMsg = new MusicMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setMusicUrl("nusic URL");
		replyMsg.setHqMusicUrl("HQ music URL");
		return new MusicMsgParser().toXML(replyMsg);
	}

	@Override
	public String processNewsMsg(String token, NewsMessage msg) {
		NewsMessage replyMsg = new NewsMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setArticleCount(2);
		
		Article a1 = new Article();
		a1.setTitle("New 1");
		a1.setDescription("DESC A1");
		a1.setPicUrl("pic.taobao.com/aaas/asdf.jpg");
		a1.setUrl("http://www.baidu.com");
		
		Article a2 = new Article();
		a2.setTitle("New 2");
		a2.setDescription("DESC A2");
		a2.setPicUrl("pic2.taobao.com/aaas/asdf222.jpg");
		a2.setUrl("http://www.yousas.com");
		
		Article[] articles = {a1,a2};
		
		replyMsg.setArticles(articles);
		return new NewsMsgParser().toXML(replyMsg);
	}

	@Override
	public String processTextMsg(String token, TextMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Text message replay.");
		return new TextMsgParser().toXML(replyMsg);
	}

	@Override
	public String processClickEvent(String token, EventMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Event Click message replay.");
		return new TextMsgParser().toXML(replyMsg);
	}

	@Override
	public String processVideoMsg(String mmt_token, VideoMessage msg) {
		VideoMessage replyMsg = new VideoMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setMediaId(msg.getMediaId());
		replyMsg.setThumbMediaId(msg.getThumbMediaId());
		return new VideoMsgParser().toXML(replyMsg);
	}

	@Override
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		VoiceMessage replyMsg = new VoiceMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setFormat(msg.getFormat());
		replyMsg.setMediaId(msg.getMediaId());
		replyMsg.setRecognition(msg.getRecognition());
		return new VoiceMsgParser().toXML(replyMsg);
	}

}
