package lemon.weixin.log.mapper;

import java.util.List;

import lemon.shared.access.bean.SiteAccess;
import lemon.weixin.log.bean.MsgLog;
import lemon.weixin.log.bean.SubscribeLog;
import lemon.weixin.log.bean.UnSubscribeLog;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * This class is for WeiXin's log
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface WXLogManager {
	/**
	 * Save site access log
	 * 
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_siteaccess(cust_id,signature,timestamp,nonce,echostr,token) SELECT #{cust_id},#{signature},#{timestamp},#{nonce},#{echostr},#{token}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveSiteAccessLog(SiteAccess log);

	/**
	 * save message log
	 * 
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_msg(cust_id,msgType,msg) SELECT #{cust_id},#{msgType},#{msg}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveMessageLog(MsgLog log);
	
	/**
	 * save subscribe log
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_subscribe(cust_id,wxid) SELECT #{cust_id},#{wxid}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveSubscribeLog(SubscribeLog log);
	
	/**
	 * save unsubscribe log
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_unsubscribe(cust_id,wxid) SELECT #{cust_id},#{wxid}")
	void saveUnSubscribeLog(UnSubscribeLog log);

	/**
	 * query for site access log 
	 * 
	 * @return
	 */
	// TODO query for site access log
	List<SiteAccess> listSiteAccessLogs(int cust_id);

	/**
	 * query for message log
	 * 
	 * @return
	 */
	// TODO query for message log
	List<MsgLog> listMsgLogs(int cust_id);

	/**
	 * delete site access log
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM weixin_log_siteaccess WHERE id=#{id}")
	void deleteSiteAccessLog(int id);

	/**
	 * delete message log
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM weixin_log_msg WHERE id=#{id}")
	void deleteMsgLog(int id);
}
