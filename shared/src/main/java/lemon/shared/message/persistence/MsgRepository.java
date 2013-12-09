package lemon.shared.message.persistence;

import java.util.List;

import lemon.shared.message.metadata.IMessage;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.Article;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;
import lemon.shared.service.ServiceType;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * message repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface MsgRepository {
	/**
	 * get receive detail message
	 * @param cust_id
	 * @param service_type
	 * @param msgType
	 * @param start
	 * @param limit
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<IMessage> getRecvMsgList(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type,
			@Param("msgType") String msgType, @Param("start") int start,
			@Param("limit") int limit);
	
	/**
	 * get receive text message
	 * @param ids
	 * @return
	 */
	List<TextMessage> getRecvTextMsgList(long[] ids);
	
	/**
	 * get receive detail message count
	 * @param cust_id
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int getRecvMsgCnt(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type,
			@Param("msgType") String msgType);

	/**
	 * get receive text message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	TextMessage getRecvTextMsg(long id);
	
	/**
	 * get receive YiXin audio message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	YXAudioMessage getRecvYXAudioMsg(long id);
	
	/**
	 * get receive event message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	EventMessage getRecvEventMsg(long id);
	
	/**
	 * get receive image message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	ImageMessage getRecvImageMsg(long id);
	
	/**
	 * get receive link message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	LinkMessage getRecvLinkMsg(long id);
	
	/**
	 * get receive location message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	LocationMessage getRecvLocationMsg(long id);
	
	/**
	 * get receive WeiXin video message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	WXVideoMessage getRecvWXVideoMsg(long id);
	
	/**
	 * get receive YiXin video message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	YXVideoMessage getRecvYXVideoMsg(long id);
	
	/**
	 * get receive WeiXin voice message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	WXVoiceMessage getRecvWXVoiceMsg(long id);
	
	/**
	 * get receive YiXin music message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	YXMusicMessage getRecvYXMusicMsg(long id);
	
	/**
	 * get send news message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	NewsMessage getSendNewsMsg(long id);
	
	/**
	 * get articles by news id
	 * @param news_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Article> getArticlesByNews(long news_id);
	
	/**
	 * get send text message by message id
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	TextMessage getSendTextMsg(long id);
	
	/**
	 * save receive YiXin audio message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvYXAudioMsg(YXAudioMessage msg);
	
	/**
	 * save receive event message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvEventMsg(EventMessage msg);
	
	/**
	 * save receive image message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvImageMsg(ImageMessage msg);
	
	/**
	 * save receive link message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvLinkMsg(LinkMessage msg);
	
	/**
	 * save receive location message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvLocationMsg(LocationMessage msg);

	/**
	 * save receive message detail
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvMsgDetail(Message msg);

	/**
	 * save receive text message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvTextMsg(TextMessage msg);
	
	/**
	 * save receive WeiXin video message
	 * 
	 * @param msg
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvWXVideoMessage(WXVideoMessage msg);
	
	/**
	 * save receive YiXin video message
	 * 
	 * @param msg
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvYXVideoMessage(YXVideoMessage msg);

	/**
	 * save receive voice message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvWXVoiceMsg(WXVoiceMessage msg);
	
	/**
	 * save receive YiXin music message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvYXMusicMsg(YXMusicMessage msg);
	
	/**
	 * save send message detail
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveSendMsgDetail(Message msg);
	
	/**
	 * save send news's articles
	 * @param cust_id
	 * @param detail_id
	 * @param article
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveSendNewsArticles(@Param("cust_id") int cust_id,
			@Param("detail_id") long detail_id,
			@Param("article") Article article);
	
	/**
	 * save send news message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveSendNewsMsg(NewsMessage msg);
	
	/**
	 * save text message detail
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveSendTextMsg(TextMessage msg);
}
