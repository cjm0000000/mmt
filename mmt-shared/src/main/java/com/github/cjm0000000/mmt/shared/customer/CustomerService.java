package com.github.cjm0000000.mmt.shared.customer;

import com.github.cjm0000000.mmt.core.MmtBase;
import com.github.cjm0000000.mmt.core.config.Status;

/**
 * MMT customer service entity
 * @author lemon
 * @version 1.0
 *
 */
public class CustomerService extends MmtBase {
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
