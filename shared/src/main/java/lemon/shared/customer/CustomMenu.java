package lemon.shared.customer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 自定义菜单
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenu {
	private int menu_id;
	private int cust_id;
	@NotEmpty(message="菜单名称不能为空")
	@Length(max = 20, min = 2, message = "菜单名称长度必须在 2 - 16 位之间")
	private String name;
	private byte menulevcod;
	private int supmenucode;
	private String key;
	@NotEmpty(message="按钮类型不能为空")
	private String type;
	@Max(value=9999,message="排序号不能超过9999.")
	@Min(value=0,message="排序号不能小于0.")
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
