package lemon.yixin.bean;

import lemon.shared.common.MMTConfig;

/**
 * Customer's configures of YiXin
 * @author lemon
 * @version 1.0
 *
 */
public class YiXinConfig implements MMTConfig {
	/** MMT客户编号 */
	private int cust_id;
	/** 客户在易信接口填写的TOKEN */
	private String token;
	/** MMT客户易信号 */
	private String yx_account;
	/** 时间戳 */
	private String timestamp;
	/** 业务代码实现 */
	private String biz_class;
	/** 欢迎信息 */
	private String welcome_msg;
	/** 用户订阅时推送的消息 */
	private String subscribe_msg;
	/** 客户在易信的第三方用户唯一凭证 */
	private String appid;
	/** 客户在易信的第三方用户唯一凭证密钥 */
	private String secret;
	/** 客户易信API的URL，系统内唯一 */
	private String api_url;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getYx_account() {
		return yx_account;
	}
	public void setYx_account(String yx_account) {
		this.yx_account = yx_account;
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
	public String getBiz_class() {
		return biz_class;
	}
	public void setBiz_class(String biz_class) {
		this.biz_class = biz_class;
	}
	public String getSubscribe_msg() {
		return subscribe_msg;
	}
	public void setSubscribe_msg(String subscribe_msg) {
		this.subscribe_msg = subscribe_msg;
	}
	public String getWelcome_msg() {
		return welcome_msg;
	}
	public void setWelcome_msg(String welcome_msg) {
		this.welcome_msg = welcome_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getApi_url() {
		return api_url;
	}
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("cust_id=");
		sb.append(cust_id);
		sb.append(",token=");
		sb.append(token);
		sb.append(",yx_account=");
		sb.append(yx_account);
		sb.append(",timestamp=");
		sb.append(timestamp);
		sb.append(",bizClass=");
		sb.append(biz_class);
		sb.append(",subscribe_msg=");
		sb.append(subscribe_msg);
		return sb.toString();
	}
}
