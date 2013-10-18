package lemon.yixin.fans.bean;

import lemon.shared.entity.Status;

/**
 * Bean for WeiXin fans
 * @author lemon
 * @version 1.0
 */
public class YiXinFans {
	private int id;
	private String yxid;
	private int cust_id;
	private String nick_name;
	private Status status;
	private String timestamp;
	
	public String getYxid() {
		return yxid;
	}
	public void setYxid(String yxid) {
		this.yxid = yxid;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
