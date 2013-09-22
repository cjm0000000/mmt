package lemon.web.crm.yxapi;

import lemon.shared.biz.MMTRobot;
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
	private MMTRobot mmtRobot;

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
		replyMsg.setContent("打开链接会不会中毒？怕怕");
		//save send log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		//receive location message
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("嗯哼？你的行踪已经被我知道了。");
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processTextMsg(String mmt_token, TextMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		YiXinConfig cfg = YiXin.getConfig(mmt_token);
		//生成回复消息
		String reply = mmtRobot.reply(cfg.getCust_id(), msg.getContent());
		if(null == reply)
			reply = getWelcome(cfg);
		replyMsg.setContent(reply);
		
		//save log
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
		replyMsg.setContent("嘿嘿，我是近视眼，看不清楚呢。");
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processAudioMsg(String mmt_token, AudioMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("嗯哼，你在说什么，我的听力还没有全部发育哟^");
		
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processMusicMsg(String mmt_token, MusicMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("嗯哼，歌儿真好听哟。");
		
		//save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

}
