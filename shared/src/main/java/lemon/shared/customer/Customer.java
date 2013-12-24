package lemon.shared.customer;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.cjm0000000.mmt.core.config.Status;

/**
 * Customer entity
 * @author lemon
 * @version 1.0
 *
 */
public class Customer {
	private int cust_id;
	@NotEmpty(message="客户名称不能为空")
	@Length(max = 20, min = 2, message = "客户名称长度必须在 2 - 20 位之间")
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
