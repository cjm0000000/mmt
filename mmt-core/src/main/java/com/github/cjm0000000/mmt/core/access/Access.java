package com.github.cjm0000000.mmt.core.access;

import com.github.cjm0000000.mmt.core.MmtBase;

/**
 * Site access object
 * @author lemon
 * @version 1.0
 *
 */
public class Access extends MmtBase {
	private String signature;
	private String timestamp_api;
	private String nonce;
	private String echostr;
	private String token;
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp_api() {
		return timestamp_api;
	}
	public void setTimestamp_api(String timestamp_api) {
		this.timestamp_api = timestamp_api;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
