package lemon.shared.log.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import lemon.shared.log.bean.AccessTokenLog;
import lemon.shared.log.bean.CustomMenuLog;

/**
 * MMT log manager
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface MMTLogManager {
	/**
	 * save access token log
	 * @param log
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert("INSERT INTO mmt_log_accesstoken(cust_id,service_type,appid,secret,grant_type,result) SELECT #{cust_id},#{service_type},#{appid},#{secret},#{grant_type},#{result}")
	@Lang(RawLanguageDriver.class)
	int saveAccessTokenLog(AccessTokenLog log);
	
	/**
	 * save custom menu log
	 * @param log
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert("INSERT INTO mmt_log_custommenu(cust_id,service_type,action,access_token,msg,result) SELECT #{cust_id},#{service_type},#{action},#{access_token},#{msg},#{result}")
	@Lang(RawLanguageDriver.class)
	int saveCustomMenuLog(CustomMenuLog log);
}
