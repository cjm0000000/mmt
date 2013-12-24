package lemon.shared.customer;

import com.github.cjm0000000.mmt.core.config.Status;

import lemon.shared.service.BaseService;

/**
 * MMT customer service entity
 * @author lemon
 * @version 1.0
 *
 */
public class CustomerService extends BaseService {
	private Status status;
	private int expire_time;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}
	
}
