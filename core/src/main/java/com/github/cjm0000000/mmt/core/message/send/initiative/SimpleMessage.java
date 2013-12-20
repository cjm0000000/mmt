package com.github.cjm0000000.mmt.core.message.send.initiative;

import com.github.cjm0000000.mmt.core.BaseService;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * simple message for send initiative
 * @author lemon
 * @version 2.0
 *
 */
public class SimpleMessage extends BaseService {
	@MmtAlias("touser")
	protected String toUser;
	@MmtAlias("msgtype")
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
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
