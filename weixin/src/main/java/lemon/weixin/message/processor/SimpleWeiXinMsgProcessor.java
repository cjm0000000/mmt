package lemon.weixin.message.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.weixin.WeiXinException;

import lemon.shared.config.MMTConfig;
import lemon.shared.message.MMTRobot;
import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.AudioMessage;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.VideoMessage;
import lemon.shared.message.metadata.VoiceMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.MusicMessage;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.parser.NewsMsgParser;
import lemon.shared.message.parser.TextMsgParser;
import lemon.shared.message.processor.AbstractMsgProcessor;
import lemon.shared.service.ServiceType;
import lemon.weixin.config.WeiXin;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.toolkit.WeatherAdapter;

/**
 * 简单的微信消息机器人
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service
public final class SimpleWeiXinMsgProcessor extends AbstractMsgProcessor {
	@Autowired
	private MsgManager msgManager;
	@Autowired
	private NewsMsgParser newsMsgParser;
	@Autowired
	private TextMsgParser textMsgParser;
	@Autowired
	private MMTRobot mmtRobot;
	@Autowired
	private WeatherAdapter weatherAdapter;
	
	@Override
	public MMTConfig getConfig(String mmt_token) {
		return WeiXin.getConfig(mmt_token);
	}
	
	public ServiceType getServiceType(){
		return ServiceType.WEIXIN;
	}

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
		//TODO 对于地理位置消息，暂时先发送天气消息，以后可以考虑图文发送美食，天气等消息
		NewsMessage replyMsg = new NewsMessage();
		buildReplyMsg(msg, replyMsg);
		//查询天气信息
		String cityName = msg.getLabel().split("市")[0];
		replyMsg = weatherAdapter.generateWeatherReport(cityName, replyMsg);
		if(replyMsg == null)
			throw new WeiXinException("地理位置消息处理失败。");
		// save log
		replyMsg.setService_type(getServiceType());
		msgManager.saveSendNewsMsg(replyMsg);
		return newsMsgParser.toXML(replyMsg);
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
		if (!(msg instanceof WXVideoMessage))
			throw new WeiXinException("不是正确的视频格式。");
		return sendTextMessage((WXVideoMessage)msg, "亲，我暂时无法识别视频信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		if (!(msg instanceof WXVoiceMessage))
			throw new WeiXinException("不是正确的语音格式。");
		return sendTextMessage((WXVoiceMessage) msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processAudioMsg(String mmt_token, AudioMessage msg) {
		throw new WeiXinException("微信不支持接收Audio消息。");
	}

	@Override
	public String processMusicMsg(String mmt_token, MusicMessage msg) {
		throw new WeiXinException("微信不支持接收Music消息。");
	}

}