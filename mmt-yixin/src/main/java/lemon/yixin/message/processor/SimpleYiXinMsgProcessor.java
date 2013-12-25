package lemon.yixin.message.processor;

import lemon.shared.message.MMTRobot;
import lemon.shared.message.metadata.AudioMessage;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.VideoMessage;
import lemon.shared.message.metadata.VoiceMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.MusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.message.parser.TextMsgParser;
import lemon.shared.message.processor.AbstractMsgProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.yixin.YiXin;
import com.github.cjm0000000.mmt.yixin.YiXinException;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;

@Service
public class SimpleYiXinMsgProcessor extends AbstractMsgProcessor {
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
		if (!(msg instanceof YXVideoMessage))
			throw new YiXinException("不是正确的视频格式。");
		return sendTextMessage((YXVideoMessage)msg, "亲，我暂时无法识别视频信息哦，您可以给我发文字消息。");
	}

	@Override
	public String processAudioMsg(String mmt_token, AudioMessage msg) {
		if (!(msg instanceof YXAudioMessage))
			throw new YiXinException("不是正确的音频格式。");
		return sendTextMessage((YXAudioMessage)msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}
	
	@Override
	public String processMusicMsg(String mmt_token, MusicMessage msg) {
		if (!(msg instanceof YXMusicMessage))
			throw new YiXinException("不是正确的音乐格式。");
		return sendTextMessage((YXMusicMessage)msg, "亲，我暂时无法识别音乐哦，您可以给我发文字消息。");
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.YIXIN;
	}

	@Override
	public MmtConfig getConfig(String mmt_token) {
		return YiXin.getConfig(mmt_token);
	}

	@Override
	public String processVoiceMsg(String mmt_token, VoiceMessage msg) {
		throw new YiXinException("微信不支持接收Voice消息。");
	}
}
