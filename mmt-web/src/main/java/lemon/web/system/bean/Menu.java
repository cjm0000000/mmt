package lemon.web.system.bean;

/**
 * 存放系统菜单
 * @author 张连明
 * @date Mar 17, 2012 3:01:19 PM
 *
 */
public class Menu {
	private String menu_code;
	private String menu_name;
	private String menulevcod;
	private String supmenucode;
	private String menuurl;
	private String menuico;
	private int sort;
	private String iconCls;
	private String authority;
	
	public Menu(){}

	public Menu(String menu_code, String menu_name, String menulevcod,
			String supmenucode, String menuurl, String menuico, int sort,
			String iconCls) {
		super();
		this.menu_code = menu_code;
		this.menu_name = menu_name;
		this.menulevcod = menulevcod;
		this.supmenucode = supmenucode;
		this.menuurl = menuurl;
		this.menuico = menuico;
		this.sort = sort;
		this.iconCls = iconCls;
	}
	public Menu(String menu_code, String menu_name, String menulevcod,
			String supmenucode, String menuico,String authority) {
		super();
		this.menu_code = menu_code;
		this.menu_name = menu_name;
		this.menulevcod = menulevcod;
		this.supmenucode = supmenucode;
		this.menuico = menuico;
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

	public String getMenulevcod() {
		return menulevcod;
	}

	public void setMenulevcod(String menulevcod) {
		this.menulevcod = menulevcod;
	}

	public String getSupmenucode() {
		return supmenucode;
	}

	public void setSupmenucode(String supmenucode) {
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
