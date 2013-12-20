package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * base message entity
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class SimpleRecvMessage extends SimpleMessageService {
	/** MsgId */
	@MmtAlias("MsgId")
	protected long msgId;
	
	public SimpleRecvMessage(String msgType) {
		super(msgType);
	}
	
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("[toUserName=").append(toUserName);
		sb.append(", fromUserName=").append(fromUserName);
		sb.append(", createTime=").append(createTime);
		sb.append(", msgType=").append(msgType);
		sb.append(", msgId=").append(msgId).append("]");
		return sb.toString();
	}

}
