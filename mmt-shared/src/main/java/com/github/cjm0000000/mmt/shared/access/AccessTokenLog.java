package com.github.cjm0000000.mmt.shared.access;

import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * Access token log entity
 * @author lemon
 * @version 1.0
 *
 */
public class AccessTokenLog extends MmtService {
	private String appid;
	private String secret;
	private String grant_type;
	private String result;
	
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
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
