package lemon.shared.weixin.bean;

/**
 * Some configs with weixin
 * @author lemon
 *
 */
public class WeiXinConfig {
	/** MMT客户编号 */
	private int cust_id;
	/** 接入需要提供的TOKEN */
	private String token;
	/** 开发者微信号 */
	private String wx_account;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getWx_account() {
		return wx_account;
	}
	public void setWx_account(String wx_account) {
		this.wx_account = wx_account;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
}
