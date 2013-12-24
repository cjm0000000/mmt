package lemon.shared.message.log;

import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * Message log
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MsgLog extends MmtService{
	private String msg;

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
