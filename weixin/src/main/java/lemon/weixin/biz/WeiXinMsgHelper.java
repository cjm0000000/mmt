package lemon.weixin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.VideoMessage;
import lemon.weixin.bean.message.VoiceMessage;
import lemon.weixin.bean.message.WeiXinMessage;
import lemon.weixin.dao.WXRecvMsgDetailMapper;

/**
 * WeiXin message helper
 * @author lemon
 *
 */
@Service
public final class WeiXinMsgHelper {
	@Autowired
	private WXRecvMsgDetailMapper recvMsgMapper;
	
	/**
	 * save received WeiXin event message
	 * @param msg
	 */
	public void saveRecvEventMsg(EventMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveEventMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin image message
	 * @param msg
	 */
	public void saveRecvImageMsg(ImageMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveImageMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin link message
	 * @param msg
	 */
	public void saveRecvLinkMsg(LinkMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveLinkMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin location message
	 * @param msg
	 */
	public void saveRecvLocationMsg(LocationMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveLocationMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin text message
	 * @param msg
	 */
	public void saveRecvTextMsg(TextMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveTextMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin video message
	 * @param msg
	 */
	public void saveRecvVideoMsg(VideoMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveVideoMessageDetail(msg);
	}
	
	/**
	 * save received WeiXin voice message
	 * @param msg
	 */
	public void saveRecvVoiceMsg(VoiceMessage msg){
		saveWeiXinMsg(msg);
		recvMsgMapper.saveVoiceMsgDetail(msg);
	}
	
	/**
	 * save send WeiXin music message
	 * @param msg
	 */
	public void saveSendMusicMsg(MusicMessage msg){
		saveWeiXinMsg(msg);
		//TODO save send music message
	}
	
	/**
	 * save send WeiXin music message
	 * @param msg
	 */
	public void saveSendNewsMsg(NewsMessage msg){
		saveWeiXinMsg(msg);
		//TODO save send news message
	}
	
	/**
	 * save send WeiXin text message
	 * @param msg
	 */
	public void saveSendTextMsg(TextMessage msg){
		saveWeiXinMsg(msg);
		//TODO save send text message
	}
	
	/**
	 * save WeiXin common message 
	 * @param msg
	 */
	private void saveWeiXinMsg(WeiXinMessage msg){
		recvMsgMapper.saveMsgDetail(msg);
	}
}
