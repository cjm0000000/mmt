package lemon.weixin.biz.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.biz.MMTRobot;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.bean.message.WeiXinMessage;
import lemon.weixin.biz.WeiXinMsgHelper;
import lemon.weixin.biz.parser.TextMsgParser;

/**
 * 简单的微信消息机器人
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service
public class SimpleWeiXinMsgProcessor extends WXCustBasicMsgProcessor {
	@Autowired
	private WeiXinMsgHelper msgHelper;
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
		WeiXinConfig cfg = WeiXin.getConfig(mmt_token);
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
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}
	
	/**
	 * 发送文本消息
	 * @param msg
	 * @param content
	 * @return
	 */
	protected String sendTextMessage(WeiXinMessage msg,String content){
		TextMessage replyMsg = new TextMessage();
		buildReplyMsg(msg, replyMsg);
		replyMsg.setContent(content);
		// save log
		msgHelper.saveSendTextMsg(replyMsg);
		return textMsgParser.toXML(replyMsg);
	}

}
