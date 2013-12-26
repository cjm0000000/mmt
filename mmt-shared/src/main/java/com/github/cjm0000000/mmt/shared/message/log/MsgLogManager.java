package com.github.cjm0000000.mmt.shared.message.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.shared.message.log.persistence.MsgLogRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;

/**
 * Message log manager
 * @author lemon
 * @version 1.0
 *
 */
@Service
public final class MsgLogManager {
	@Autowired
	private MsgLogRepository msgLogRepository;
	/**
	 * 保存消息发送日志
	 * @param log
	 * @return
	 */
	public int saveRecvLog(MsgLog log){
		prepareMsgLogId(log);
		return msgLogRepository.saveMsgLog("recv", log);
	}
	
	/**
	 * 保存消息接收日志
	 * @param log
	 * @return
	 */
	public int saveSendLog(MsgLog log){
		prepareMsgLogId(log);
		return msgLogRepository.saveMsgLog("send", log);
	}
	
	/**
	 * 生成消息日志ID
	 * @param log
	 */
	private void prepareMsgLogId(MsgLog log){
		log.setId(IdWorkerManager.getIdWorker(MsgLog.class).getId());
	}
}
