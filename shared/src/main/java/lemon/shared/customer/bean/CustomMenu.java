package lemon.shared.customer.bean;

/**
 * 自定义菜单
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenu {
	private int menu_id;
	private int cust_id;
	private String name;
	private byte menulevcod;
	private int supmenucode;
	private String key;
	private String type;
	private int sort;
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getMenulevcod() {
		return menulevcod;
	}
	public void setMenulevcod(byte menulevcod) {
		this.menulevcod = menulevcod;
	}
	public int getSupmenucode() {
		return supmenucode;
	}
	public void setSupmenucode(int supmenucode) {
		this.supmenucode = supmenucode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
