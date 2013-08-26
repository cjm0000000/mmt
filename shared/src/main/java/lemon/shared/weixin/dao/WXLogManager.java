package lemon.shared.weixin.dao;

import java.util.List;

import lemon.shared.weixin.bean.MsgLog;
import lemon.shared.weixin.bean.SiteAccessLog;

import org.springframework.stereotype.Repository;

/**
 * This class is for weixin's transaction log
 * @author lemon
 *
 */
@Repository
public interface WXLogManager {
	/**
	 * 保存网址接入Log
	 */
	void saveSiteAccessLog(SiteAccessLog log);
	/**
	 * save message log
	 * @param log
	 */
	void saveMessageLog(MsgLog log);
	
	/**
	 * 查询接入日志
	 * @return
	 */
	List<SiteAccessLog> listSiteAccessLogs();
	
	/**
	 * 查询交易日志
	 * @return
	 */
	List<MsgLog> listMsgLogs();
}
