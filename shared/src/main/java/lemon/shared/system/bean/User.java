package lemon.shared.system.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录实例
 * @author 张连明
 * @date Mar 14, 2012 2:39:13 PM
 */
public class User implements Serializable {
	private static final long serialVersionUID = -8663731569993336678L;
	private int user_id;
	private String password;
	private String user_name;
	private String sfzh;
	private String sjhm;
	private Date lastlogintime;
	private String islock;
	private String bz;
	private String lastloginip;
	private String status;
	private String dept_code;
	private String dept_name;
	private int role_id;
	private String role_name;
	private String xm;
	private String reload;
	private String sessionId;
	
	public User(){}

	public User(int user_id, String password, String user_name, String sfzh,
			String sjhm, Date lastlogintime, String islock, String bz,
			String lastloginip, String status, String dept_code,
			String dept_name, int role_id, String role_name, String xm) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.user_name = user_name;
		this.sfzh = sfzh;
		this.sjhm = sjhm;
		this.lastlogintime = lastlogintime;
		this.islock = islock;
		this.bz = bz;
		this.lastloginip = lastloginip;
		this.status = status;
		this.dept_code = dept_code;
		this.dept_name = dept_name;
		this.role_id = role_id;
		this.role_name = role_name;
		this.xm = xm;
	}
	
	public User(int user_id, String password, String user_name, String sfzh,
			String sjhm, Date lastlogintime, String islock, String bz,
			String lastloginip, String status, String dept_code,
			String dept_name, int role_id, String role_name, String xm,
			String reload) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.user_name = user_name;
		this.sfzh = sfzh;
		this.sjhm = sjhm;
		this.lastlogintime = lastlogintime;
		this.islock = islock;
		this.bz = bz;
		this.lastloginip = lastloginip;
		this.status = status;
		this.dept_code = dept_code;
		this.dept_name = dept_name;
		this.role_id = role_id;
		this.role_name = role_name;
		this.xm = xm;
		this.reload = reload;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getReload() {
		return reload;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}
}
