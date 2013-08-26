package lemon.shared.log.bean;

import java.util.Date;
/**
 * 记录登录日志
 * @author 张连明
 * @date Mar 15, 2012 10:48:20 AM
 */
public class LoginLog {
	private int user_id;
	private String user_name;
	private int role_id;
	private String dept_id;
	private Date logintime;
	private String loginstatus;
	private String loginip;
	
	public LoginLog(){}
	public LoginLog(int user_id, String user_name, int role_id, String dept_id,
			Date logintime, String loginstatus, String loginip) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.role_id = role_id;
		this.dept_id = dept_id;
		this.logintime = logintime;
		this.loginstatus = loginstatus;
		this.loginip = loginip;
	}
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
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public Date getLogintime() {
		return (Date) logintime.clone();
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	public String getLoginstatus() {
		return loginstatus;
	}
	public void setLoginstatus(String loginstatus) {
		this.loginstatus = loginstatus;
	}
	public String getLoginip() {
		return loginip;
	}
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}
	
}
