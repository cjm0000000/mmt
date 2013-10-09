package lemon.shared.customer.bean;

import java.util.List;

import lemon.shared.entity.Status;

/**
 * Customer entity
 * @author lemon
 * @version 1.0
 *
 */
public class Customer {
	private int cust_id;
	private String cust_name;
	private String memo;
	private Status status;
	private List<CustomerService> services;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<CustomerService> getServices() {
		return services;
	}

	public void setServices(List<CustomerService> services) {
		this.services = services;
	}
	
}
