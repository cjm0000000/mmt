package lemon.weixin.log.mapper;

import java.util.List;

import lemon.shared.log.bean.SiteAccess;
import lemon.weixin.log.bean.MsgLog;

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
	 * save message log
	 * 
	 * @param log
	 */
	@Insert("INSERT INTO weixin_log_msg(cust_id,msgType,msg) SELECT #{cust_id},#{msgType},#{msg}")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveMessageLog(MsgLog log);
	
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
}
