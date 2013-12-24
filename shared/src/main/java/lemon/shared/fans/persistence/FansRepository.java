package lemon.shared.fans.persistence;

import lemon.shared.fans.Fans;
import lemon.shared.fans.log.FansLog;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * Fans repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface FansRepository {
	/**
	 * save fans
	 * 
	 * @param fans
	 */
	@Lang(RawLanguageDriver.class)
	int saveFans(Fans fans);

	/**
	 * get fans by user_id, service type and cust_id
	 * @param cust_id
	 * @param service_type
	 * @param user_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	Fans getFans(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type,
			@Param("user_id") String user_id);

	/**
	 * update fans
	 * 
	 * @param fans
	 */
	int updateFans(Fans fans);
	
	/**
	 * save fans's log
	 * @param log
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveFansLog(FansLog log);
}
