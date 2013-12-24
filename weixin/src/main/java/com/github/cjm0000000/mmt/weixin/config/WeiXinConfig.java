package com.github.cjm0000000.mmt.weixin.config;

import com.github.cjm0000000.mmt.core.config.MmtConfig;

/**
 * Customer's configures of WeiXin
 * @author lemon
 * @version 1.0
 *
 */
public class WeiXinConfig extends MmtConfig {
	/** MMT客户微信号 */
	private String wx_account;
	/** 账户类别 */
	private AccountType account_type;
	/** 客户在微信的第三方用户唯一凭证 */
	private String appid;
	/** 客户在微信的第三方用户唯一凭证密钥 */
	private String secret;
	
	public String getWx_account() {
		return wx_account;
	}
	public void setWx_account(String wx_account) {
		this.wx_account = wx_account;
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
	public AccountType getAccount_type() {
		return account_type;
	}
	public void setAccount_type(AccountType account_type) {
		this.account_type = account_type;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(super.toString());
		sb.append(", account_type=").append(account_type);
		sb.append(", appid=").append(appid);
		sb.append(", secret=").append(secret);
		sb.append("]");
		return sb.toString();
	}
}
