package lemon.shared.message.log;

import lemon.shared.service.BaseService;

/**
 * Message log
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MsgLog extends BaseService{
	private String msg;

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
