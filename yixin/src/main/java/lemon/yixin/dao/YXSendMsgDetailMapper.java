package lemon.yixin.dao;

import lemon.yixin.bean.message.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;


/**
 * YiXin send message repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface YXSendMsgDetailMapper {
	/**
	 * save message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO yixin_sendmsg_detail(cust_id,toUserName,fromUserName,createTime,msgType) SELECT #{cust_id},#{toUserName},#{fromUserName},#{createTime},#{msgType}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveMsgDetail(YiXinMessage msg);

	/**
	 * save music message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO yixin_sendmsg_music(detail_id,musicUrl,hqMusicUrl) SELECT #{id},#{musicUrl},#{hqMusicUrl}")
	void saveMusicMsgDetail(MusicMessage msg);

	/**
	 * save news message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO yixin_sendmsg_news(detail_id,articleCount) SELECT #{id},#{articleCount}")
	void saveNewsMsgDetail(NewsMessage msg);
	
	
	/**
	 * save news's articles
	 * @param article
	 */
	@Insert("INSERT INTO yixin_sendmsg_news_article(detail_id,title,description,picUrl,url) SELECT #{id},#{title},#{description},#{picUrl},#{url}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveNewsArticles(Article article);
	
	/**
	 * save text message detail
	 * 
	 * @param msg
	 */
	@Insert("INSERT INTO yixin_sendmsg_text(detail_id,content) SELECT #{id},#{content}")
	void saveTextMsgDetail(TextMessage msg);
}
