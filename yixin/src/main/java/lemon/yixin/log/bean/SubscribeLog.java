package lemon.yixin.log.bean;

/**
 * YiXin User subscribe log
 * @author lemon
 * @version 1.0
 */
public class SubscribeLog {
	private int id;
	private int cust_id;
	private String yxid;
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
	public String getYxid() {
		return yxid;
	}
	public void setYxid(String yxid) {
		this.yxid = yxid;
	}
	public String getSubscribe_date() {
		return subscribe_date;
	}
	public void setSubscribe_date(String subscribe_date) {
		this.subscribe_date = subscribe_date;
	}
	
}
