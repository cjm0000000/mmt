package lemon.weixin.log.bean;

/**
 * WeiXin User subscribe log
 * @author lemon
 * @version 1.0
 */
public class SubscribeLog {
	private int id;
	private int cust_id;
	private String wxid;
	private String subscribe_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getWxid() {
		return wxid;
	}
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}
	public String getSubscribe_date() {
		return subscribe_date;
	}
	public void setSubscribe_date(String subscribe_date) {
		this.subscribe_date = subscribe_date;
	}
	
}
