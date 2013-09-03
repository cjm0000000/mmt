package lemon.weixin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.biz.customer.WXCustBasicMsgProcessor;
import lemon.weixin.biz.parser.TextMsgParser;
import lemon.weixin.biz.parser.VideoMsgParser;
import lemon.weixin.biz.parser.VoiceMsgParser;

/**
 * Lemon message test business
 * @author lemon
 *
 */
@Service
public class LemonMessageBiz extends WXCustBasicMsgProcessor {
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private VideoMsgParser videoMsgParser;
	@Autowired
	private VoiceMsgParser voiceMsgParser;
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
		replyMsg.setMediaId(msg.getMediaId());
		replyMsg.setThumbMediaId(msg.getThumbMediaId());
		return videoMsgParser.toXML(replyMsg);
	}

	@Override
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		VoiceMessage replyMsg = new VoiceMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setFormat(msg.getFormat());
		replyMsg.setMediaId(msg.getMediaId());
		replyMsg.setRecognition(msg.getRecognition());
		return voiceMsgParser.toXML(replyMsg);
	}

}
