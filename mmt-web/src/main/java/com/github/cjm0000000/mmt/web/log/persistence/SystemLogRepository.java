package com.github.cjm0000000.mmt.web.log.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.log.LoginLog;

/**
 * system log manager
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface SystemLogRepository {
	/**
	 * save login log
	 * @param log
	 */
	@Insert("INSERT INTO system_login_log(user_id,user_name,role_id,loginstatus,loginip) VALUES(#{user_id},#{user_name},#{role_id},#{loginstatus},#{loginip})")
	int saveLoginLog(LoginLog log);
	
	/**
	 * query login log by user_id
	 * @param user_id
	 * @param start
	 * @param limit
	 * @return
	 */
	@Select("SELECT A.id,A.user_id,A.user_name,A.role_id,A.logintime,A.loginstatus,A.loginip FROM system_login_log A WHERE A.user_id=#{user_id} limit #{start},#{limit} ")
	List<LoginLog> getLoginLog(@Param("user_id")int user_id, @Param("start")int start, @Param("limit")int limit);
}