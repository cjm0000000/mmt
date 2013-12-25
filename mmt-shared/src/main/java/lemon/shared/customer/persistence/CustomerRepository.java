package lemon.shared.customer.persistence;

import java.util.List;

import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * Customer repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface CustomerRepository {
	
	/**
	 * Get customer by id
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	Customer getCustomer(int cust_id);
	
	/**
	 * Get customer list
	 * @param start
	 * @param limit
	 * @param cust_name
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Customer> getCustomerList(@Param("start") int start,
			@Param("limit") int limit, @Param("cust_name")String cust_name);
	
	/**
	 * Get customer count
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int getCustCnt(@Param("cust_name")String cust_name);
	
	/**
	 * Add Customer
	 * @param cust
	 */
	@Lang(RawLanguageDriver.class)
	int addCustomer(Customer cust);
	
	/**
	 * Update customer
	 * @param cust
	 */
	@Lang(RawLanguageDriver.class)
	int updateCustomer(Customer cust);
	
	/**
	 * Delete customer
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int delete(int cust_id); 
	
	/**
	 * add customer service
	 * @param service
	 */
	@Lang(RawLanguageDriver.class)
	int addService(CustomerService service);
	
	/**
	 * get customer service by id
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	CustomerService getServiceById(long id);
	
	/**
	 * get customer service by service type
	 * @param cust_id
	 * @param service
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	CustomerService getService(@Param("cust_id") int cust_id,
			@Param("service_type") ServiceType service_type);
	
	/**
	 * get all the services of the customer
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<CustomerService> getServices(int cust_id);
	
	/**
	 * delete customer's service by cust_id and service type
	 * @param cust_id
	 * @param service
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int deleteService(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
}
