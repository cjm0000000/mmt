package lemon.shared.weixin.bean;

import java.util.Date;

/**
 * Message log
 * 
 * @author lemon
 * 
 */
public class MsgLog {
	private int id;
	private String msgType;
	private String msg;
	private Date log_date;

	private MsgLog(String msgType, String msg) {
		this.msg = msg;
		this.msgType = msgType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getLog_date() {
		return log_date;
	}

	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public static MsgLog createReciveLog(String msg) {
		return new MsgLog("RECIVE", msg);
	}
	public static MsgLog createSendLog(String msg) {
		return new MsgLog("SEND", msg);
	}
}
