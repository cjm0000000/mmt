package lemon.shared.message.persistence;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.event.EventMessage;
import lemon.shared.message.metadata.recv.ImageMessage;
import lemon.shared.message.metadata.recv.LinkMessage;
import lemon.shared.message.metadata.recv.LocationMessage;
import lemon.shared.message.metadata.send.Article;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXMusicMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.metadata.specific.yixin.YXAudioMessage;
import lemon.shared.message.metadata.specific.yixin.YXMusicMessage;
import lemon.shared.message.metadata.specific.yixin.YXVideoMessage;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	 * get receive text message
	 * @param id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.toUserName,A.fromUserName,A.createTime,A.msgType,A.msgId, B.content FROM msg_recv_detail A, msg_recv_text B WHERE A.id=B.detail_id AND A.msgType='text' AND A.id=#{id}")
	TextMessage getRecvTextMsg(int id);
	
	/**
	 * save receive YiXin audio message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_audio_yixin(detail_id,cust_id,url,name,mimeType) VALUES(#{id},#{cust_id},#{url},#{name},#{mimeType})")
	@Lang(RawLanguageDriver.class)
	int saveRecvYXAudioMsg(YXAudioMessage msg);
	
	/**
	 * save receive event message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_event(detail_id,cust_id,eventType,eventKey) VALUES(#{id},#{cust_id},#{eventType},#{eventKey})")
	@Lang(RawLanguageDriver.class)
	int saveRecvEventMsg(EventMessage msg);
	
	/**
	 * save receive image message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_image(detail_id,picUrl,mediaId) VALUES (#{id},#{picUrl},#{mediaId})")
	@Lang(RawLanguageDriver.class)
	int saveRecvImageMsg(ImageMessage msg);
	
	/**
	 * save receive link message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_link(detail_id,title,description,url) VALUES (#{id}, #{title}, #{description},#{url})")
	@Lang(RawLanguageDriver.class)
	int saveRecvLinkMsg(LinkMessage msg);
	
	/**
	 * save receive location message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_location(detail_id,location_X,location_Y,scale,label) VALUES (#{id}, #{location_X}, #{location_Y},#{scale},#{label})")
	@Lang(RawLanguageDriver.class)
	int saveRecvLocationMsg(LocationMessage msg);

	/**
	 * save receive message detail
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_detail(id,cust_id,service_type,toUserName,fromUserName,createTime,msgType,msgId,timestamp) VALUES (#{id},#{cust_id},#{service_type},#{toUserName},#{fromUserName},#{createTime},#{msgType},#{msgId},now())")
	@Lang(RawLanguageDriver.class)
	int saveRecvMsgDetail(Message msg);

	/**
	 * save receive text message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_text(detail_id,content) VALUES (#{id},#{content})")
	@Lang(RawLanguageDriver.class)
	int saveRecvTextMsg(TextMessage msg);
	
	/**
	 * save receive WeiXin video message
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO msg_recv_video_weixin(detail_id,mediaId,thumbMediaId) VALUES (#{id}, #{mediaId}, #{thumbMediaId})")
	@Lang(RawLanguageDriver.class)
	int saveRecvWXVideoMessage(WXVideoMessage msg);
	
	/**
	 * save receive YiXin video message
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO msg_recv_video_yixin(detail_id,url,name,mimeType) VALUES (#{id}, #{url}, #{name}, #{mimeType})")
	@Lang(RawLanguageDriver.class)
	int saveRecvYXVideoMessage(YXVideoMessage msg);

	/**
	 * save receive voice message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_voice_weixin(detail_id,mediaId,format,recognition) VALUES (#{id}, #{mediaId}, #{format},#{recognition})")
	@Lang(RawLanguageDriver.class)
	int saveRecvWXVoiceMsg(WXVoiceMessage msg);
	
	/**
	 * save receive YiXin music message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_recv_music_yixin(detail_id,url,name,mimeType,`desc`) VALUES (#{id},#{url},#{name},#{mimeType},#{desc})")
	@Lang(RawLanguageDriver.class)
	int saveRecvYXMusicMsg(YXMusicMessage msg);
	
	/**
	 * save send message detail
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_send_detail(id,cust_id,service_type,toUserName,fromUserName,createTime,msgType,timestamp) VALUES (#{id},#{cust_id},#{service_type},#{toUserName},#{fromUserName},#{createTime},#{msgType},now())")
	@Lang(RawLanguageDriver.class)
	int saveSendMsgDetail(Message msg);
	
	/**
	 * save send YiXin music message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_send_music_yixin(detail_id,musicUrl,hqMusicUrl) VALUES (#{id},#{musicUrl},#{hqMusicUrl})")
	@Lang(RawLanguageDriver.class)
	int saveSendYXMusicMsg(YXMusicMessage msg);
	
	/**
	 * save send WeiXin music message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_send_music_weixin(detail_id,musicUrl,hqMusicUrl) VALUES (#{id},#{musicUrl},#{hqMusicUrl})")
	@Lang(RawLanguageDriver.class)
	int saveSendWXMusicMsg(WXMusicMessage msg);
	
	/**
	 * save send news's articles
	 * @param detail_id
	 * @param article
	 * @return
	 */
	@Insert("INSERT INTO msg_send_news_article(id,detail_id,title,description,picUrl,url) VALUES (#{article.id},#{detail_id},#{article.title},#{article.description},#{article.picUrl},#{article.url})")
	@Lang(RawLanguageDriver.class)
	int saveSendNewsArticles(@Param("detail_id") long detail_id,
			@Param("article") Article article);
	
	/**
	 * save send news message
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_send_news(detail_id,articleCount) VALUES (#{id},#{articleCount})")
	@Lang(RawLanguageDriver.class)
	int saveSendNewsMsg(NewsMessage msg);
	
	/**
	 * save text message detail
	 * @param msg
	 * @return
	 */
	@Insert("INSERT INTO msg_send_text(detail_id,content) VALUES (#{id},#{content})")
	@Lang(RawLanguageDriver.class)
	int saveSendTextMsg(TextMessage msg);
}
