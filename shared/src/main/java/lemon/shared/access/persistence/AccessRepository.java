package lemon.shared.access.persistence;

import lemon.shared.access.AccessToken;
import lemon.shared.access.AccessTokenLog;
import lemon.shared.access.Access;
import lemon.shared.service.ServiceType;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * Access repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface AccessRepository {
	
	/**
	 * save access log
	 * @param access
	 * @return
	 */
	@Insert("INSERT INTO access_log(id,cust_id,service_type,signature,nonce,echostr,token,timestamp_api) VALUES(#{id},#{cust_id},#{service_type},#{signature},#{nonce},#{echostr},#{token},#{timestamp_api})")
	@Lang(RawLanguageDriver.class)
	int saveAccessLog(Access access);
	
	/**
	 * delete the access token by customer id and service type
	 * @param cust_id
	 * @param service_type
	 * @return
	 */
	@Delete("DELETE FROM access_token WHERE cust_id=#{cust_id} AND service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	int deleteAccessToken(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
	
	/**
	 * get the access token by customer id and service type
	 * @param cust_id
	 * @param service_type
	 * @return
	 */
	@Select("SELECT A.cust_id,A.service_type,A.access_token,A.expire_time,A.timestamp FROM access_token A WHERE A.cust_id=#{cust_id} AND A.service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	AccessToken getAccessToken(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
	
	/**
	 * save access token
	 * @param token
	 * @return
	 */
	@Insert("INSERT INTO access_token(cust_id,service_type,access_token,expire_time) VALUES(#{cust_id},#{service_type},#{access_token},#{expire_time})")
	@Lang(RawLanguageDriver.class)
	int saveAccessToken(AccessToken token);
	
	/**
	 * save access token log
	 * @param log
	 * @return
	 */
	@Insert("INSERT INTO access_token_log(id,cust_id,service_type,appid,secret,grant_type,result) VALUES(#{id},#{cust_id},#{service_type},#{appid},#{secret},#{grant_type},#{result})")
	@Lang(RawLanguageDriver.class)
	int saveAccessTokenLog(AccessTokenLog log);
}
