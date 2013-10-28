package lemon.shared.message;

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
import lemon.shared.message.metadata.specific.weixin.WXMusicMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.message.persistence.MsgRepository;

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
	 * get receive text message by id
	 * @param msg_id
	 * @return
	 */
	public TextMessage getRecvTextMsg(int msg_id){
		return msgRepository.getRecvTextMsg(msg_id);
	}
	
	/**
	 * save received audio message
	 * @param msg
	 */
	public void saveRecvAudioMsg(AudioMessage msg) {
		if (!(msg instanceof YXAudioMessage))
			return;
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
			saveSendMsg(wxMsg);
			msgRepository.saveRecvWXVideoMessage(wxMsg);
		}else if(msg instanceof YXVideoMessage){
			YXVideoMessage yxMsg = (YXVideoMessage) msg;
			saveSendMsg(yxMsg);
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
	public void saveSendMusicMsg(MusicMessage msg){
		if(msg instanceof WXMusicMessage){
			WXMusicMessage wxMsg = (WXMusicMessage) msg;
			saveSendMsg(wxMsg);
			msgRepository.saveSendWXMusicMsg(wxMsg);
		}else if(msg instanceof YXMusicMessage){
			YXMusicMessage yxMsg = (YXMusicMessage) msg;
			saveSendMsg(yxMsg);
			msgRepository.saveSendYXMusicMsg(yxMsg);
		}
		
	}
	
	/**
	 * save send music message
	 * @param msg
	 */
	public void saveSendNewsMsg(NewsMessage msg){
		saveSendMsg(msg);
		msgRepository.saveSendNewsMsg(msg);
		for (Article article : msg.getArticles()) {
			article.setId(msg.getId());
			msgRepository.saveSendNewsArticles(article);
		}
	}
	
	/**
	 * save send text message
	 * @param msg
	 */
	public void saveSendTextMsg(TextMessage msg){
		saveSendMsg(msg);
		msgRepository.saveSendTextMsg(msg);
	}
	
	/**
	 * save common receive message 
	 * @param msg
	 */
	private void saveRecvMsg(Message msg){
		msgRepository.saveRecvMsgDetail(msg);
	}
	
	/**
	 * save common send message 
	 * @param msg
	 */
	private void saveSendMsg(Message msg){
		msgRepository.saveSendMsgDetail(msg);
	}
}
