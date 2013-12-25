package lemon.shared.message.log.persistence;

import lemon.shared.message.log.MsgLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Message log repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface MsgLogRepository {

	/**
	 * save message log
	 * @param method
	 * @param log
	 * @return
	 */
	@Insert("INSERT INTO msg_${method}_log(id,cust_id,service_type,msg,timestamp) VALUES(#{log.id},#{log.cust_id},#{log.service_type},#{log.msg},now())")
	int saveMsgLog(@Param("method") String method, @Param("log") MsgLog log);
}
