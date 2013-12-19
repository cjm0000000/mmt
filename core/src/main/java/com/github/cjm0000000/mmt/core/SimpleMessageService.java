package com.github.cjm0000000.mmt.core;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtCDATA;

/**
 * Simple message entity
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class SimpleMessageService extends BaseService {
	/** ToUserName */
	@MmtCDATA
	@MmtAlias("ToUserName")
	protected String toUserName;
	/** FromUserName */
	@MmtCDATA
	@MmtAlias("FromUserName")
	protected String fromUserName;
	/** CreateTime */
	@MmtAlias("CreateTime")
	protected String createTime;
	/** MsgType */
	@MmtCDATA
	@MmtAlias("MsgType")
	protected String msgType;
	
	public SimpleMessageService(){}
	
	public SimpleMessageService(String msgType) {
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("[toUserName=").append(toUserName);
		sb.append(", fromUserName=").append(fromUserName);
		sb.append(", createTime=").append(createTime);
		sb.append(", msgType=").append(msgType);
		return sb.toString();
	}

}
