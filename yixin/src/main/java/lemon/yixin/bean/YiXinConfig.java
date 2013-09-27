package lemon.yixin.bean;

import lemon.shared.api.simple.MMTConfig;

/**
 * Customer's configures of YiXin
 * @author lemon
 * @version 1.0
 *
 */
public class YiXinConfig extends MMTConfig {
	/** MMT客户易信号 */
	private String yx_account;
	/** 客户在易信的第三方用户唯一凭证 */
	private String appid;
	/** 客户在易信的第三方用户唯一凭证密钥 */
	private String secret;
	
	public String getYx_account() {
		return yx_account;
	}
	public void setYx_account(String yx_account) {
		this.yx_account = yx_account;
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
