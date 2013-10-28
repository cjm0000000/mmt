package lemon.web.log.mapper;

import java.util.List;

import lemon.web.log.bean.LoginLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * system log manager
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface SystemLogManager {
	/**
	 * save login log
	 * @param log
	 */
	@Insert("INSERT INTO system_login_log(user_id,user_name,role_id,loginstatus,loginip) SELECT #{user_id},#{user_name},#{role_id},#{loginstatus},#{loginip}")
	void saveLoginLog(LoginLog log);
	
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