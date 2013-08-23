package lemon.shared.weixin.dao;

import java.util.List;

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
	
	List<SiteAccessLog> list();
}
