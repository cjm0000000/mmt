package lemon.web.system.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.cjm0000000.mmt.core.config.Status;

/**
 * role entity
 * @author lemon
 * @date Mar 20, 2012 1:18:13 PM
 * @version 1.0
 */
public class Role {
	private int role_id;
	@NotEmpty(message = "角色名称不能为空")
	@Length(max = 30, min = 2, message = "角色名称长度必须在 2 - 30 位之间")
	private String role_name;
	private String role_desc;
	private Status status;
	@Max(value=9999,message="排序号不能超过9999.")
	@Min(value=0,message="排序号不能小于0.")
	private int sort;
	private Status reloadable;
	
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
	public String getRole_desc() {
		return role_desc;
	}
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Status getReloadable() {
		return reloadable;
	}
	public void setReloadable(Status reloadable) {
		this.reloadable = reloadable;
	}
	
}
