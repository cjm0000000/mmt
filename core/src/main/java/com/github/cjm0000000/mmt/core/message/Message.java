package com.github.cjm0000000.mmt.core.message;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Interface for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class Message extends BaseService {
	/** ToUserName */
	@MmtAlias("ToUserName")
	protected String toUserName;
	/** FromUserName */
	@MmtAlias("FromUserName")
	protected String fromUserName;
	/** CreateTime */
	@MmtAlias("CreateTime")
	protected int createTime;
	/** MsgType */
	@MmtAlias("MsgType")
	protected String msgType;
	/** MsgId */
	@MmtAlias("MsgId")
	protected Long msgId;
	
	public Message(){}
	
	public Message(String msgType) {
		this.msgType = msgType;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[toUserName=").append(toUserName);
		sb.append(", fromUserName=").append(fromUserName);
		sb.append(", createTime=").append(createTime);
		sb.append(", msgType=").append(msgType);
		sb.append(", msgId=").append(msgId).append("]");
		return sb.toString();
	}

}
