package lemon.weixin.log.bean;

/**
 * WeiXin user unsubscribe log
 * @author lemon
 * @version 1.0
 */
public class UnSubscribeLog {
	private int id;
	private int cust_id;
	private String wxid;
	private String unsubscribe_date;
	
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
	public String getUnsubscribe_date() {
		return unsubscribe_date;
	}
	public void setUnsubscribe_date(String unsubscribe_date) {
		this.unsubscribe_date = unsubscribe_date;
	}
	
}
