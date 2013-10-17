package lemon.web.system.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Menu entity
 * @author lemon
 * @version 1.0
 *
 */
public class Menu {
	private int menu_id;
	@NotEmpty(message="菜单名称不能为空")
	@Length(max = 10, min = 2, message = "菜单名称长度必须在 2 - 10 位之间")
	private String menu_name;
	private String menulevcod;
	private int supmenucode;
	private String menuurl;
	private String menuico;
	@Max(value=9999,message="排序号不能超过9999.")
	@Min(value=0,message="排序号不能小于0.")
	private int sort;
	private String authority;
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenulevcod() {
		return menulevcod;
	}
	public void setMenulevcod(String menulevcod) {
		this.menulevcod = menulevcod;
	}
	public int getSupmenucode() {
		return supmenucode;
	}
	public void setSupmenucode(int supmenucode) {
		this.supmenucode = supmenucode;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public String getMenuico() {
		return menuico;
	}
	public void setMenuico(String menuico) {
		this.menuico = menuico;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
