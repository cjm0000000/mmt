package lemon.weixin.bean;

/**
 * Some configures with WeiXin
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
	/** 时间戳 */
	private String timestamp;
	/** 业务代码实现 */
	private String bizClass;
	
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getBizClass() {
		return bizClass;
	}
	public void setBizClass(String bizClass) {
		this.bizClass = bizClass;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("cust_id=");
		sb.append(cust_id);
		sb.append(",token=");
		sb.append(token);
		sb.append(",wx_account=");
		sb.append(wx_account);
		sb.append(",timestamp=");
		sb.append(timestamp);
		return sb.toString();
	}
}
