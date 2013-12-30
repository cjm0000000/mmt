package com.github.cjm0000000.mmt.shared.message.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.LinkMessage;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.TextMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VoiceMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.AudioMessage;
import com.github.cjm0000000.mmt.core.message.recv.yixin.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.node.NewsNode;
import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;
import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.message.event.EventDetail;

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
	List<BaseMessage> getRecvMsgList(@Param("cust_id") int cust_id,
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
	 * get receive message detail
	 * @param id
	 * @return
	 */
	 BaseMessage getRecvMsgDetail(long id);

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
	AudioMessage getRecvAudioMsg(long id);
	
	/**
	 * get receive event message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	EventDetail getRecvEvent(long id);
	
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
	com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage getRecvWXVideoMsg(long id);
	
	/**
	 * get receive YiXin video message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage getRecvYXVideoMsg(long id);
	
	/**
	 * get receive WeiXin voice message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	VoiceMessage getRecvVoiceMsg(long id);
	
	/**
	 * get receive YiXin music message
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	MusicMessage getRecvYXMusicMsg(long id);
	
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
	List<NewsNode> getArticlesByNews(long news_id);
	
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
	int saveRecvAudioMsg(AudioMessage msg);
	
	/**
	 * save receive event message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvEventMsg(EventDetail msg);
	
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
	int saveRecvMsgDetail(SimpleRecvMessage msg);

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
	int saveRecvWXVideoMessage(com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage msg);
	
	/**
	 * save receive YiXin video message
	 * 
	 * @param msg
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvYXVideoMessage(com.github.cjm0000000.mmt.core.message.recv.yixin.VideoMessage msg);

	/**
	 * save receive voice message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvVoiceMsg(VoiceMessage msg);
	
	/**
	 * save receive YiXin music message
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveRecvYXMusicMsg(MusicMessage msg);
	
	/**
	 * save send message detail
	 * @param msg
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveSendMsgDetail(BaseMessage msg);
	
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
			@Param("article") NewsNode article);
	
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
	int saveSendTextMsg(com.github.cjm0000000.mmt.core.message.send.passive.TextMessage msg);
}
