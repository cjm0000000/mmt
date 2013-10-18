package lemon.weixin.message.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import lemon.weixin.message.bean.EventMessage;
import lemon.weixin.message.bean.ImageMessage;
import lemon.weixin.message.bean.LinkMessage;
import lemon.weixin.message.bean.LocationMessage;
import lemon.weixin.message.bean.TextMessage;
import lemon.weixin.message.bean.VideoMessage;
import lemon.weixin.message.bean.VoiceMessage;
import lemon.weixin.message.bean.WeiXinMessage;

/**
 * WeiXin receive message repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface WXRecvMsgDetailMapper {
	/**
	 * save message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_detail(cust_id,toUserName,fromUserName,createTime,msgType,msgId) SELECT #{cust_id}, #{toUserName}, #{fromUserName}, #{createTime}, #{msgType}, #{msgId}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveMsgDetail(WeiXinMessage msg);

	/**
	 * save text message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_text(detail_id,content) SELECT #{id}, #{content}")
	void saveTextMsgDetail(TextMessage msg);

	/**
	 * save image message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_image(detail_id,picUrl,mediaId) SELECT #{id}, #{picUrl}, #{mediaId}")
	void saveImageMsgDetail(ImageMessage msg);

	/**
	 * save location message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_location(detail_id,location_X,location_Y,scale,label) SELECT #{id}, #{location_X}, #{location_Y},#{scale},#{label}")
	void saveLocationMsgDetail(LocationMessage msg);

	/**
	 * save event message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_event(detail_id,eventType,eventKey) SELECT #{id}, #{eventType}, #{eventKey}")
	void saveEventMsgDetail(EventMessage msg);

	/**
	 * save link message detail(暂时无用)
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_link(detail_id,title,description,url) SELECT #{id}, #{title}, #{description},#{url}")
	void saveLinkMsgDetail(LinkMessage msg);
	/**
	 *save voice message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_voice(detail_id,mediaId,format,recognition) SELECT #{id}, #{mediaId}, #{format},#{recognition}")
	void saveVoiceMsgDetail(VoiceMessage msg);
	/**
	 * save video message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO weixin_recvmsg_video(detail_id,mediaId,thumbMediaId) SELECT #{id}, #{mediaId}, #{thumbMediaId}")
	void saveVideoMessageDetail(VideoMessage msg);
	
	/**
	 * get text message
	 * @param id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.toUserName,A.fromUserName,A.createTime,A.msgType,A.msgId, B.content FROM weixin_recvmsg_detail A, weixin_recvmsg_text B WHERE A.id=B.detail_id AND A.msgType='text' AND A.id=#{id}")
	TextMessage getTextMsg(int id);
}
