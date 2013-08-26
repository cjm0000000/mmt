package lemon.shared.log.mapper;

import lemon.shared.log.bean.LoginLog;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogMapper {
	/**
	 * 保存日志
	 * @param log
	 */
	public void saveLog(LoginLog login);
}
