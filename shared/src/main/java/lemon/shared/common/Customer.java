package lemon.shared.common;

/**
 * Customer
 * @author lemon
 *
 */
public class Customer {
	
	private int cust_id;
	
	private String cust_name;
	
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
