package lemon.yixin.bean.log;

import java.util.Date;

/**
 * YiXin Message log
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MsgLog {
	private int id;
	private int cust_id;
	private String msgType;
	private String msg;
	private Date log_date;

	private MsgLog(int cust_id, String msgType, String msg) {
		this.cust_id = cust_id;
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

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public static MsgLog createReciveLog(int cust_id,String msg) {
		return new MsgLog(cust_id, "RECEIVE", msg);
	}
	public static MsgLog createSendLog(int cust_id, String msg) {
		return new MsgLog(cust_id, "SEND", msg);
	}
}
