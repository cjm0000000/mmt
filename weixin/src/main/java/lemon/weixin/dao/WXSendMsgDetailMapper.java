package lemon.weixin.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.bean.message.WeiXinMessage;

/**
 * WeiXin message's detail
 * 
 * @author lemon
 * 
 */
@Repository
public interface WXSendMsgDetailMapper {
	// FIXME save message detail to database

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
	void saveImageMsgDetail(ImageMessage msg);

	/**
	 * save location message detail
	 * 
	 * @param msg
	 */
	void saveLocationMsgDetail(LocationMessage msg);

	/**
	 * save event message detail
	 * 
	 * @param msg
	 */
	void saveEventMsgDetail(EventMessage msg);

	/**
	 * save link message detail
	 * 
	 * @param msg
	 */
	void saveLinkMsgDetail(LinkMessage msg);

	/**
	 * save music message detail
	 * 
	 * @param msg
	 */
	void saveMusicMsgDetail(MusicMessage msg);

	/**
	 * save news message detail
	 * 
	 * @param msg
	 */
	void saveNewsMessageDetail(NewsMessage msg);
}
