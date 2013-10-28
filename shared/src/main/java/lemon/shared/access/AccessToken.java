package lemon.shared.access;

import lemon.shared.service.BaseService;

/**
 * API返回的Token
 * @author lemon
 * @version 1.0
 *
 */
public class AccessToken extends BaseService {
	private String access_token;
	private int expires_in;
	private int expire_time;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}
}
