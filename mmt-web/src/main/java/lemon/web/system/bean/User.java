package lemon.web.system.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

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
	@NotEmpty(message="密码不能为空")
	private String password;
	@NotEmpty(message="用户名不能为空")
	@Length(max = 30, min = 3, message = "用户名长度必须在 3 - 30 位之间")
	private String user_name;
	private String idcard;
	private String mphone;
	private Status islock;
	private String bz;
	private Status status;
	private String xm;
	private int role_id;
	private String role_name;
	private int cust_id;
	private String cust_name;
	
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
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
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
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
}
