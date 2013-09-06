package lemon.web.system.bean;

import lemon.shared.entity.Status;


/**
 * User entity
 * @author lemon
 * @date Mar 14, 2012 2:39:13 PM
 * @version 1.0
 * 
 */
public class User {
	private int user_id;
	private String password;
	private String user_name;
	private String sfzh;
	private String sjhm;
	private Status islock;
	private String bz;
	private Status status;
	private int role_id;
	private String role_name;
	private String xm;
	
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

	public Status getIslock() {
		return islock;
	}

	public void setIslock(Status islock) {
		this.islock = islock;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("user_id=" +user_id);
		sb.append("|password=" +password);
		sb.append("|user_name=" +user_name);
		sb.append("|sfzh=" +sfzh);
		sb.append("|sjhm=" +sjhm);
		sb.append("|islock=" +islock);
		sb.append("|bz=" +bz);
		sb.append("|status=" +status);
		sb.append("|role_id=" +role_id);
		sb.append("|role_name=" +role_name);
		sb.append("|xm=" +xm);
		return sb.toString();
	}

}
