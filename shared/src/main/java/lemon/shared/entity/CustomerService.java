package lemon.shared.entity;

/**
 * MMT customer service entity
 * @author lemon
 * @version 1.0
 *
 */
public class CustomerService {
	private int id;
	private int cust_id;
	private ServiceType service;
	private Status status;
	private String expire_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public ServiceType getService() {
		return service;
	}
	public void setService(ServiceType service) {
		this.service = service;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	
}
