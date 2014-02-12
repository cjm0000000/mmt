package com.github.cjm0000000.mmt.shared.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.shared.message.local.persistence.LocalMsgBeanRepository;

/**
 * 消息机器人
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service
public class MMTRobot {
	@Autowired
	private LocalMsgBeanRepository msgBeanMapper;
	
	/**
	 * 智能生成回复内容
	 * @param cust_id
	 * @param msg
	 * @return
	 */
	public String reply(int cust_id, String msg) {
		if(null == msg || msg.equals(""))
			return null;
		String replyMsg;
		// 绝对匹配，如果匹配到则返回
		replyMsg = getL1Msg(cust_id, msg);
		if (null != replyMsg)
			return replyMsg;
		// 模糊匹配，如果匹配到则返回
		replyMsg = getL2Msg(cust_id, msg);
		if (null != replyMsg)
			return replyMsg;
		//匹配通用库
		replyMsg = getL3Msg(msg);
		if (null != replyMsg)
			return replyMsg;
		return null;
	}

	/**
	 * 获取一级消息
	 * @param cust_id
	 * @param msg
	 * @return
	 */
	private String getL1Msg(int cust_id, String msg) {
		return msgBeanMapper.getL1Msg(cust_id, msg) == null ? null
				: msgBeanMapper.getL1Msg(cust_id, msg).getValue();
	}

	/**
	 * 获取二级消息
	 * @param cust_id
	 * @param msg
	 * @return
	 */
	private String getL2Msg(int cust_id, String msg) {
		return msgBeanMapper.getL2Msg(cust_id, msg) == null ? null
				: msgBeanMapper.getL2Msg(cust_id, msg).getValue();
	}

	/**
	 * 获取通用消息
	 * @param msg
	 * @return
	 */
	private String getL3Msg(String msg) {
		return msgBeanMapper.getL3Msg(msg) == null ? null : 
			msgBeanMapper.getL3Msg(msg).getValue();
	}
	
}
