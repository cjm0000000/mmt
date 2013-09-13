package lemon.yixin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.yixin.bean.message.*;
import lemon.yixin.dao.YXRecvMsgDetailMapper;
import lemon.yixin.dao.YXSendMsgDetailMapper;

/**
 * WeiXin message helper
 * @author lemon
 * @version 1.0
 *
 */
@Service
public final class YiXinMsgHelper {
	@Autowired
	private YXRecvMsgDetailMapper recvMsgMapper;
	@Autowired
	private YXSendMsgDetailMapper sendMsgMapper;
	
	/**
	 * get receive text message by id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getRecvTextMsg(int msg_id){
		return recvMsgMapper.getTextMsg(msg_id);
	}
	
	/**
	 * save received WeiXin event message
	 * @param msg
	 */
	public void saveRecvEventMsg(EventMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveEventMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin image message
	 * @param msg
	 */
	public void saveRecvImageMsg(ImageMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveImageMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin link message
	 * @param msg
	 */
	public void saveRecvLinkMsg(LinkMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveLinkMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin location message
	 * @param msg
	 */
	public void saveRecvLocationMsg(LocationMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveLocationMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin text message
	 * @param msg
	 */
	public void saveRecvTextMsg(TextMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveTextMsgDetail(msg);
	}
	
	/**
	 * save received WeiXin video message
	 * @param msg
	 */
	public void saveRecvVideoMsg(VideoMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveVideoMessageDetail(msg);
	}
	
	/**
	 * save received WeiXin voice message
	 * @param msg
	 */
	public void saveRecvVoiceMsg(VoiceMessage msg){
		saveRecvMsg(msg);
		recvMsgMapper.saveVoiceMsgDetail(msg);
	}
	
	/**
	 * save send WeiXin music message
	 * @param msg
	 */
	public void saveSendMusicMsg(MusicMessage msg){
		saveSendMsg(msg);
		sendMsgMapper.saveMusicMsgDetail(msg);
	}
	
	/**
	 * save send WeiXin music message
	 * @param msg
	 */
	public void saveSendNewsMsg(NewsMessage msg){
		saveSendMsg(msg);
		sendMsgMapper.saveNewsMsgDetail(msg);
		for (Article article : msg.getArticles()) {
			article.setId(msg.getId());
			sendMsgMapper.saveNewsArticles(article);
		}
	}
	
	/**
	 * save send WeiXin text message
	 * @param msg
	 */
	public void saveSendTextMsg(TextMessage msg){
		saveSendMsg(msg);
		sendMsgMapper.saveTextMsgDetail(msg);
	}
	
	/**
	 * save WeiXin common receive message 
	 * @param msg
	 */
	private void saveRecvMsg(YiXinMessage msg){
		recvMsgMapper.saveMsgDetail(msg);
	}
	
	/**
	 * save WeiXin common receive message 
	 * @param msg
	 */
	private void saveSendMsg(YiXinMessage msg){
		sendMsgMapper.saveMsgDetail(msg);
	}
}
