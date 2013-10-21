package lemon.shared.request.mapper;

import lemon.shared.entity.ServiceType;
import lemon.shared.request.bean.AccessToken;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * Access token repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface AccessTokenMapper {
	/**
	 * save access token
	 * @param token
	 * @return
	 */
	@Insert("INSERT INTO mmt_request_accesstoken(cust_id,service_type,access_token,expire_time) SELECT #{cust_id},#{service_type},#{access_token},#{expire_time}")
	@Lang(RawLanguageDriver.class)
	int save(AccessToken token);
	
	/**
	 * get the access token by customer id and service type
	 * @param cust_id
	 * @param service_type
	 * @return
	 */
	@Select("SELECT A.cust_id,A.service_type,A.access_token,A.expire_time,A.timestamp FROM mmt_request_accesstoken A WHERE A.cust_id=#{cust_id} AND A.service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	AccessToken get(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
	
	/**
	 * delete the access token by customer id and service type
	 * @param cust_id
	 * @param service_type
	 * @return
	 */
	@Delete("DELETE FROM mmt_request_accesstoken WHERE cust_id=#{cust_id} AND service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	int delete(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
}
