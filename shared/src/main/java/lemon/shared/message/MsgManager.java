package lemon.shared.message;

import java.util.List;

import lemon.shared.MmtException;
import lemon.shared.message.metadata.AudioMessage;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.VideoMessage;
import lemon.shared.message.metadata.VoiceMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.Article;
import lemon.shared.message.metadata.send.MusicMessage;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.message.persistence.MsgRepository;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Message manager
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class MsgManager {
	@Autowired
	private MsgRepository msgRepository;
	
	/**
	 * get receive text message by message id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getRecvTextMsg(long msg_id){
		return msgRepository.getRecvTextMsg(msg_id);
	}
	
	/**
	 * get receive YiXin audio message by message id
	 * @param msg_id
	 * @return
	 */
	public YXAudioMessage getRecvYXAudioMsg(long msg_id){
		return msgRepository.getRecvYXAudioMsg(msg_id);
	}
	
	/**
	 * get receive event message by message id
	 * @param msg_id
	 * @return
	 */
	public EventMessage getRecvEventMsg(long msg_id){
		return msgRepository.getRecvEventMsg(msg_id);
	}
	
	/**
	 * get receive image message by message id
	 * @param msg_id
	 * @return
	 */
	public ImageMessage getRecvImageMsg(long msg_id){
		return msgRepository.getRecvImageMsg(msg_id);
	}
	
	/**
	 * get receive link message by message id
	 * @param msg_id
	 * @return
	 */
	public LinkMessage getRecvLinkMsg(long msg_id){
		return msgRepository.getRecvLinkMsg(msg_id);
	}
	
	/**
	 * get receive location message by message id
	 * @param msg_id
	 * @return
	 */
	public LocationMessage getRecvLocationMsg(long msg_id){
		return msgRepository.getRecvLocationMsg(msg_id);
	}
	
	/**
	 * get receive WeiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public WXVideoMessage getRecvWXVideoMsg(long msg_id){
		return msgRepository.getRecvWXVideoMsg(msg_id);
	}
	
	/**
	 * get receive YiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public YXVideoMessage getRecvYXVideoMsg(long msg_id){
		return msgRepository.getRecvYXVideoMsg(msg_id);
	}
	
	/**
	 * get receive YiXin video message by message id
	 * @param msg_id
	 * @return
	 */
	public WXVoiceMessage getRecvWXVoiceMsg(long msg_id){
		return msgRepository.getRecvWXVoiceMsg(msg_id);
	}
	
	/**
	 * get receive YiXin music message by message id
	 * @param msg_id
	 * @return
	 */
	public YXMusicMessage getRecvYXMusicMsg(long msg_id){
		return msgRepository.getRecvYXMusicMsg(msg_id);
	}
	
	/**
	 * get send news message by message id
	 * @param msg_id
	 * @return
	 */
	public NewsMessage getSendNewsMsg(long msg_id){
		return msgRepository.getSendNewsMsg(msg_id);
	}
	
	/**
	 * get send news articles by message id
	 * @param msg_id
	 * @return
	 */
	public List<Article> getArticlesByNews(long msg_id){
		return msgRepository.getArticlesByNews(msg_id);
	}
	
	/**
	 * get send text message by message id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getSendTextMsg(long msg_id){
		return msgRepository.getSendTextMsg(msg_id);
	}
	
	/**
	 * save received audio message
	 * @param msg
	 */
	public void saveRecvAudioMsg(AudioMessage msg) {
		if (!(msg instanceof YXAudioMessage))
			throw new MmtException("不是YXAudioMessage类型，无法保存。");
		saveRecvMsg((YXAudioMessage) msg);
		msgRepository.saveRecvYXAudioMsg((YXAudioMessage)msg);
	}
	
	/**
	 * save received event message
	 * @param msg
	 */
	public void saveRecvEventMsg(EventMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvEventMsg(msg);
	}
	
	/**
	 * save received image message
	 * @param msg
	 */
	public void saveRecvImageMsg(ImageMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvImageMsg(msg);
	}
	
	/**
	 * save received link message
	 * @param msg
	 */
	public void saveRecvLinkMsg(LinkMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvLinkMsg(msg);
	}
	
	/**
	 * save received location message
	 * @param msg
	 */
	public void saveRecvLocationMsg(LocationMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvLocationMsg(msg);
	}
	
	/**
	 * save received text message
	 * @param msg
	 */
	public void saveRecvTextMsg(TextMessage msg){
		saveRecvMsg(msg);
		msgRepository.saveRecvTextMsg(msg);
	}
	
	/**
	 * save received video message
	 * @param msg
	 */
	public void saveRecvVideoMsg(VideoMessage msg){
		if(msg instanceof WXVideoMessage){
			WXVideoMessage wxMsg = (WXVideoMessage) msg;
			saveRecvMsg(wxMsg);
			msgRepository.saveRecvWXVideoMessage(wxMsg);
		}else if(msg instanceof YXVideoMessage){
			YXVideoMessage yxMsg = (YXVideoMessage) msg;
			saveRecvMsg(yxMsg);
			msgRepository.saveRecvYXVideoMessage(yxMsg);
		}
	}
	
	/**
	 * save received voice message
	 * @param msg
	 */
	public void saveRecvVoiceMsg(VoiceMessage msg) {
		if (!(msg instanceof WXVoiceMessage))
			return;
		saveRecvMsg((WXVoiceMessage) msg);
		msgRepository.saveRecvWXVoiceMsg((WXVoiceMessage) msg);
	}
	
	/**
	 * save received music message
	 * @param msg
	 */
	public void saveRecvMusicMsg(MusicMessage msg){
		if (!(msg instanceof YXMusicMessage))
			return;
		saveRecvMsg((YXMusicMessage)msg);
		msgRepository.saveRecvYXMusicMsg((YXMusicMessage)msg);
	}
	
	/**
	 * save send music message
	 * @param msg
	 */
	public void saveSendNewsMsg(NewsMessage msg){
		saveSendMsg(msg);
		msgRepository.saveSendNewsMsg(msg);
		for (Article article : msg.getArticles()) {
			article.setId(IdWorkerManager.getIdWorker(Article.class).getId());
			msgRepository.saveSendNewsArticles(msg.getCust_id(), msg.getId(), article);
		}
	}
	
	/**
	 * save send text message
	 * @param msg
	 */
	public void saveSendTextMsg(TextMessage msg){
		saveSendMsg(msg);
		System.out.println(msg.getId());
		msgRepository.saveSendTextMsg(msg);
	}
	
	/**
	 * save common receive message 
	 * @param msg
	 */
	private void saveRecvMsg(Message msg){
		prepareMsgId(msg);
		msgRepository.saveRecvMsgDetail(msg);
	}
	
	/**
	 * save common send message 
	 * @param msg
	 */
	private void saveSendMsg(Message msg){
		prepareMsgId(msg);
		msgRepository.saveSendMsgDetail(msg);
	}
	
	/**
	 * 生成消息ID
	 * @param msg
	 */
	private void prepareMsgId(Message msg){
		msg.setId(IdWorkerManager.getIdWorker(Message.class).getId());
	}
}
