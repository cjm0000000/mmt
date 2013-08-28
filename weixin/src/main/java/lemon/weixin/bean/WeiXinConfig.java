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
	/** 用户订阅时推送的消息 */
	private String subscribe_msg;
	/** 用户取消订阅时推送的消息 */
	private String unsubscribe_msg;
	
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
	public String getSubscribe_msg() {
		return subscribe_msg;
	}
	public void setSubscribe_msg(String subscribe_msg) {
		this.subscribe_msg = subscribe_msg;
	}
	public String getUnsubscribe_msg() {
		return unsubscribe_msg;
	}
	public void setUnsubscribe_msg(String unsubscribe_msg) {
		this.unsubscribe_msg = unsubscribe_msg;
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
		sb.append(",bizClass=");
		sb.append(bizClass);
		sb.append(",subscribe_msg=");
		sb.append(subscribe_msg);
		sb.append(",unsubscribe_msg=");
		sb.append(unsubscribe_msg);
		return sb.toString();
	}
}
