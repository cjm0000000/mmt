package lemon.web.crm.yxapi;

import lemon.yixin.YiXin;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.bean.message.*;
import lemon.yixin.biz.YiXinMsgHelper;
import lemon.yixin.biz.customer.YXCustBasicMsgProcessor;
import lemon.yixin.biz.parser.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Lemon message test business_YiXin implement
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class MMT_YiXin_MsgProcessor extends YXCustBasicMsgProcessor {
	@Autowired
	private YiXinMsgHelper msgHelper;
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private MusicMsgParser musicMsgParser;
	@Autowired
	private NewsMsgParser newsMsgParser;

	@Override
	public String processImageMsg(String mmt_token, ImageMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("<a href='" + msg.getPicUrl() + "'>下载图片</a>");
		//save send log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processLinkMsg(String token, LinkMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("MMTChat Link message replay.");
		//save send log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		//receive location message, reply a music message
		MusicMessage replyMsg = new MusicMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setMusicUrl("MMTChat nusic URL");
		replyMsg.setHqMusicUrl("MMTChat HQ music URL");
		//save log
		msgHelper.saveSendMusicMsg(replyMsg);
		return musicMsgParser.toXML(replyMsg);
	}

	@Override
	public String processTextMsg(String mmt_token, TextMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("You said: " + msg.getContent());
		//save log
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		replyMsg.setCust_id(cfg.getCust_id());
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processClickEvent(String token, EventMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("MMTChat Event Click message replay.");
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processVideoMsg(String mmt_token, VideoMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("You send me a video, thanks!");
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		NewsMessage replyMsg = new NewsMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setArticleCount(2);
		
		Article a1 = new Article();
		a1.setTitle("MMTChat New 1");
		a1.setDescription("DESC A1");
		a1.setPicUrl("pic.taobao.com/aaas/asdf.jpg");
		a1.setUrl("http://www.baidu.com");
		
		Article a2 = new Article();
		a2.setTitle("MMTChat New 2");
		a2.setDescription("DESC A2");
		a2.setPicUrl("pic2.taobao.com/aaas/asdf222.jpg");
		a2.setUrl("http://www.yousas.com");
		Article[] articles = {a1,a2};
		replyMsg.setArticles(articles);
		//save log
		msgHelper.saveSendNewsMsg(replyMsg);
		return newsMsgParser.toXML(replyMsg);
	}

}
