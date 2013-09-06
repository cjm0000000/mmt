package lemon.web.system.bean;

/**
 * user role
 * @author 张连明
 * @date Mar 28, 2012 9:15:16 PM
 *
 */
public class UserRole {
	private int id;
	private int user_id;
	private int role_id;
	private String role_name;
	private String dept_code;
	private String dept_name;
	public UserRole(){}
	public UserRole(int id, int user_id, int role_id, String role_name,
			String dept_code, String dept_name) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.role_id = role_id;
		this.role_name = role_name;
		this.dept_code = dept_code;
		this.dept_name = dept_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
}
