package com.github.cjm0000000.mmt.core.config;

/**
 * 统一APP配置信息
 * @author lemon
 * @version 1.0
 *
 */
public class MmtConfig {
	/** MMT客户编号 */
	protected int cust_id;
	/** 客户在微信接口填写的TOKEN */
	protected String token;
	/** 时间戳 */
	protected String timestamp;
	/** 业务代码实现 */
	protected String biz_class;
	/** 用户订阅时推送的消息 */
	protected String subscribe_msg;
	/** 欢迎信息 */
	protected String welcome_msg;
	/** 客户微信API的URL，系统内唯一 */
	protected String api_url;
	
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public String getApi_url() {
		return api_url;
	}
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("cust_id=").append(cust_id);
		sb.append(", token=").append(token);
		sb.append(", api_url=").append(api_url);
		sb.append(", implement_class=").append(biz_class);
		return sb.toString();
	}
	
}
