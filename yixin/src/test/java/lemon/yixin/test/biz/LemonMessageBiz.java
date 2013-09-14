package lemon.yixin.test.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.yixin.bean.message.*;
import lemon.yixin.biz.customer.YXCustBasicMsgProcessor;
import lemon.yixin.biz.parser.*;

/**
 * Lemon message test business
 * @author lemon
 *
 */
@Service
public class LemonMessageBiz extends YXCustBasicMsgProcessor {
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private VideoMsgParser videoMsgParser;
	@Autowired
	private AudioMsgParser audioMsgParser;
	@Autowired
	private MusicMsgParser musicMsgParser;
	@Override
	public String processImageMsg(String mmt_token, ImageMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Image message replay.");
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processLinkMsg(String token, LinkMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Link message replay.");
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Location message replay.");
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processTextMsg(String token, TextMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Text message replay.");
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processClickEvent(String token, EventMessage msg) {
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent("Lemon Event Click message replay.");
		return textMsgParser.toXML(replyMsg);
	}

	@Override
	public String processVideoMsg(String mmt_token, VideoMessage msg) {
		VideoMessage replyMsg = new VideoMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setUrl(msg.getUrl());
		replyMsg.setName(msg.getName());
		return videoMsgParser.toXML(replyMsg);
	}

	@Override
	public String processAudioMsg(String mmt_token, AudioMessage msg) {
		AudioMessage replyMsg = new AudioMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setUrl(msg.getUrl());
		replyMsg.setName(msg.getName());
		return audioMsgParser.toXML(replyMsg);
	}

	@Override
	public String processMusicMsg(String mmt_token, MusicMessage msg) {
		MusicMessage replyMsg = new MusicMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setUrl(msg.getUrl());
		replyMsg.setName(msg.getName());
		replyMsg.setDesc(msg.getDesc());
		replyMsg.setMimeType(msg.getMimeType());
		return musicMsgParser.toXML(replyMsg);
	}

}
