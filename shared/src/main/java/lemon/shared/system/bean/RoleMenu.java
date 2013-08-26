package lemon.shared.system.bean;

/**
 * 封装角色权限
 * @author 张连明
 * @date Mar 29, 2012 3:42:36 PM
 */
public class RoleMenu {
	private String menu_code;
	private String menu_name;
	private String authority;
	
	public RoleMenu(){}
	public RoleMenu(String menu_code, String menu_name, String authority) {
		super();
		this.menu_code = menu_code;
		this.menu_name = menu_name;
		this.authority = authority;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
