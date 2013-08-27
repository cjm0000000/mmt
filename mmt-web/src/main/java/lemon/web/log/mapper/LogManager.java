package lemon.web.log.mapper;

import lemon.web.log.bean.LoginLog;

import org.springframework.stereotype.Repository;

@Repository
public interface LogManager {
	/**
	 * 保存日志
	 * @param log
	 */
	public void saveLog(LoginLog login);
}