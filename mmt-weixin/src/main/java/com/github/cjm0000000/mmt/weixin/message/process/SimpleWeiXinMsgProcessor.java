package com.github.cjm0000000.mmt.weixin.message.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.MsgManager;
import com.github.cjm0000000.mmt.shared.message.process.AbstractPassiveMsgProcessor;
import com.github.cjm0000000.mmt.weixin.WeiXin;
import com.github.cjm0000000.mmt.weixin.WeiXinException;
import com.github.cjm0000000.mmt.weixin.weather.WeatherAdapter;

/**
 * 简单的微信消息机器人
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service
public final class SimpleWeiXinMsgProcessor extends AbstractPassiveMsgProcessor {
	@Autowired
	private MsgManager msgManager;
	@Autowired
	private WeatherAdapter weatherAdapter;
	
	@Override
	public MmtConfig getConfig(String mmt_token) {
		return WeiXin.getConfig(mmt_token);
	}
	
	public ServiceType getServiceType(){
		return ServiceType.WEIXIN;
	}

	@Override
	protected BaseMessage processAudioMsg(MmtConfig cfg,
			com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage msg) {
		throw new WeiXinException("微信不支持接收Audio消息。");
	}

	@Override
	protected BaseMessage processImageMsg(MmtConfig cfg,
			com.github.cjm0000000.mmt.core.message.recv.ImageMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别图片信息哦，您可以给我发文字消息。");
	}

	@Override
	protected BaseMessage processLinkMsg(MmtConfig cfg,
			com.github.cjm0000000.mmt.core.message.recv.LinkMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别链接信息哦，您可以给我发文字消息。");
	}

	@Override
	protected BaseMessage processLocationMsg(MmtConfig cfg,
			com.github.cjm0000000.mmt.core.message.recv.LocationMessage msg) {
		// TODO 对于地理位置消息，暂时先发送天气消息，以后可以考虑图文发送美食，天气等消息
		NewsMessage replyMsg = new NewsMessage();
		buildReplyMsg(msg, replyMsg);
		// 查询天气信息
		String cityName = msg.getLabel().split("市")[0];
		replyMsg = weatherAdapter.generateWeatherReport(cityName, replyMsg);
		if (replyMsg == null)
			throw new WeiXinException("地理位置消息处理失败。");
		// save log
		replyMsg.setService_type(getServiceType());
		// msgManager.saveSendNewsMsg(replyMsg);
		return replyMsg;
	}

	@Override
	protected BaseMessage processVideoMsg(MmtConfig cfg, IVideo msg) {
		if (!(msg instanceof IVideo))
			throw new WeiXinException("不是正确的视频格式。");
		return sendTextMessage((BaseMessage) msg, "亲，我暂时无法识别视频信息哦，您可以给我发文字消息。");
	}

	@Override
	protected BaseMessage processVoiceMsg(MmtConfig cfg,
			com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage msg) {
		return sendTextMessage(msg, "亲，我暂时无法识别语音信息哦，您可以给我发文字消息。");
	}

}