package lemon.shared.service;

/**
 * Base element of Log
 * @author lemon
 * @version 1.0
 */
public class BaseService {
	protected int id;
	protected int cust_id;
	protected ServiceType service_type;
	protected String timestamp;
	
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
	public ServiceType getService_type() {
		return service_type;
	}
	public void setService_type(ServiceType service_type) {
		this.service_type = service_type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
