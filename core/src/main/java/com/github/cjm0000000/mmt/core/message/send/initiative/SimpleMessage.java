package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.cjm0000000.mmt.core.BaseService;

/**
 * simple message for send initiative
 * @author lemon
 * @version 2.0
 *
 */
public class SimpleMessage extends BaseService {
	@JSONField(name = "touser")
	protected String toUser;
	@JSONField(name = "msgtype")
	protected String msgType;
	
	SimpleMessage(String msgType){
		this.msgType = msgType;
	}
	
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getMsgType() {
		return msgType;
	}
	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
