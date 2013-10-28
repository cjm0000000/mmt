package lemon.shared.customer;

import lemon.shared.config.Status;
import lemon.shared.service.BaseService;

/**
 * MMT customer service entity
 * @author lemon
 * @version 1.0
 *
 */
public class CustomerService extends BaseService {
	private Status status;
	private String expire_time;
	
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
