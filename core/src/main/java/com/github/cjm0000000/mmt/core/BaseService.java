package com.github.cjm0000000.mmt.core;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtOmitField;

/**
 * Base element for customer
 * @author lemon
 * @version 1.0
 */
public class BaseService {
	@MmtOmitField
	protected long id;
	@MmtOmitField
	protected int cust_id;
	@MmtOmitField
	protected ServiceType service_type;
	@MmtOmitField
	protected String timestamp;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
