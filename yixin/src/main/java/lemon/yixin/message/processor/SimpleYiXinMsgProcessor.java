package lemon.yixin.message.processor;

import lemon.shared.api.simple.MMTRobot;
import lemon.yixin.config.YiXin;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.message.YiXinMsgHelper;
import lemon.yixin.message.bean.AudioMessage;
import lemon.yixin.message.bean.EventMessage;
import lemon.yixin.message.bean.ImageMessage;
import lemon.yixin.message.bean.LinkMessage;
import lemon.yixin.message.bean.LocationMessage;
import lemon.yixin.message.bean.MusicMessage;
import lemon.yixin.message.bean.TextMessage;
import lemon.yixin.message.bean.VideoMessage;
import lemon.yixin.message.bean.YiXinMessage;
import lemon.yixin.message.parser.TextMsgParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleYiXinMsgProcessor extends YXBasicMsgProcessor {
	@Autowired
	private YiXinMsgHelper msgHelper;
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private MMTRobot mmtRobot;

	@Override
	public String processImageMsg(String mmt_token, ImageMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别图片信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processLinkMsg(String token, LinkMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别链接信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		//FIXME 对于地理位置消息，暂时先发送天气消息，以后可以考虑图文发送美食，天气等消息
		return sendTextMessage(msg, "亲，我暂时无法识别地理位置信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processTextMsg(String mmt_token, TextMessage msg) {
		 YiXinConfig cfg =  YiXin.getConfig(mmt_token);
		// 生成回复消息
		String reply = mmtRobot.reply(cfg.getCust_id(), msg.getContent());
		if (null == reply)
			reply = getWelcome(cfg);
		return sendTextMessage(msg, reply);
		
	}

	@Override
	public String processClickEvent(String token, EventMessage msg) {
		//TODO 识别CLICK事件
		return sendTextMessage(msg, "MMTChat Event Click message replay.");
	}

	@Override
	public String processVideoMsg(String mmt_token, VideoMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别视频信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processAudioMsg(String mmt_token, AudioMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}
	
	@Override
	public String processMusicMsg(String mmt_token, MusicMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别音乐哦，您可以给我发文字消息。");
	}
	
	/**
	 * 发送文本消息
	 * @param msg
	 * @param content
	 * @return
	 */
	protected String sendTextMessage( YiXinMessage msg,String content){
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(content);
		// save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

	
}
