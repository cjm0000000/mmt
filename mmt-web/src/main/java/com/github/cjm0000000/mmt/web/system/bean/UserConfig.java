package com.github.cjm0000000.mmt.web.system.bean;

/**
 * User configure
 * @author lemon
 * @version 1.0
 *
 */
public class UserConfig {
	private int user_id;
	private String key;
	private String value;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
