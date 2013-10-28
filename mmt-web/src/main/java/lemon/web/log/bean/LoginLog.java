package lemon.web.log.bean;

import lemon.shared.config.Status;

/**
 * login log entity
 * @author lemon
 * @date Mar 15, 2012 10:48:20 AM
 * @version 1.0
 */
public class LoginLog {
	private int user_id;
	private String user_name;
	private int role_id;
	private String logintime;
	private Status loginstatus;
	private String loginip;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public Status getLoginstatus() {
		return loginstatus;
	}
	public void setLoginstatus(Status loginstatus) {
		this.loginstatus = loginstatus;
	}
	public String getLoginip() {
		return loginip;
	}
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

}