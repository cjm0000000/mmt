package lemon.weixin.dao;

import java.util.List;

import lemon.weixin.bean.log.MsgLog;
import lemon.weixin.bean.log.SiteAccessLog;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * This class is for WeiXin's log
 * 
 * @author lemon
 * 
 */
@Repository
public interface WXLogManager {
	/**
	 * 保存网址接入Log
	 * 
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_siteaccess(cust_id,signature,timestamp,nonce,echostr,token) SELECT #{cust_id},#{signature},#{timestamp},#{nonce},#{echostr},#{token}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveSiteAccessLog(SiteAccessLog log);

	/**
	 * save message log
	 * 
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_msg(cust_id,msgType,msg) SELECT #{cust_id},#{msgType},#{msg}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveMessageLog(MsgLog log);

	/**
	 * 查询接入日志
	 * 
	 * @return
	 */
	List<SiteAccessLog> listSiteAccessLogs(int cust_id);

	/**
	 * 查询消息日志
	 * 
	 * @return
	 */
	List<MsgLog> listMsgLogs(int cust_id);

	/**
	 * 删除网址接入Log
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM weixin_log_siteaccess WHERE id=#{id}")
	void deleteSiteAccessLog(int id);

	/**
	 * 删除消息日志
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM weixin_log_msg WHERE id=#{id}")
	void deleteMsgLog(int id);
}
