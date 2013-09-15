package lemon.web.system.bean;

/**
 * Menu entity
 * @author lemon
 * @date Mar 17, 2012 3:01:19 PM
 * @version 1.0
 *
 */
public class Menu {
	private int menu_id;
	private String menu_name;
	private String menulevcod;
	private int supmenucode;
	private String menuurl;
	private String menuico;
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
