package lemon.shared.system.bean;

/**
 * 角色model
 * @author 张连明
 * @date Mar 20, 2012 1:18:13 PM
 */
public class Role {
	private int role_id;
	private String role_name;
	private String zt;
	private String role_desc;
	private int sort;
	private String reload;
	
	public Role(){}
	public Role(int role_id, String role_name, String zt, String role_desc,
			int sort, String reload) {
		super();
		this.role_id = role_id;
		this.role_name = role_name;
		this.zt = zt;
		this.role_desc = role_desc;
		this.sort = sort;
		this.reload = reload;
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

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getReload() {
		return reload;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}
}
