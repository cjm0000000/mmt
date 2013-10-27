package lemon.shared.message.log;

import lemon.shared.message.log.persistence.MsgLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return msgLogRepository.saveMsgLog("recv", log);
	}
	
	/**
	 * 保存消息接收日志
	 * @param log
	 * @return
	 */
	public int saveSendLog(MsgLog log){
		return msgLogRepository.saveMsgLog("send", log);
	}
}
