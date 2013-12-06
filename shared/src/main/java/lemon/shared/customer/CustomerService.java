package lemon.shared.customer;

import java.util.Date;

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
	private Date expire_time;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}
	
}
