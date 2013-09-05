package lemon.shared.mapper;

import java.util.List;

import lemon.shared.entity.Customer;
import lemon.shared.entity.CustomerService;
import lemon.shared.entity.ServiceType;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Customer repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface CustomerMapper {
	
	/**
	 * Get customer by id
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT C.cust_id,C.cust_name,C.memo FROM customer C WHERE C.cust_id=#{cust_id}")
	Customer getCustomer(int cust_id);
	/**
	 * Get all active customer
	 * @return
	 */
	@Select("SELECT C.cust_id,C.cust_name,C.memo FROM customer C WHERE C.status='AVAILABLE'")
	List<Customer> activeCustomerList();
	/**
	 * Add Customer
	 * @param cust
	 */
	@Insert("INSERT INTO customer(cust_name,memo,status) SELECT #{cust_name},#{memo},#{status}")
	@Options(useGeneratedKeys=true,keyProperty="cust_id",keyColumn="cust_id")
	void addCustomer(Customer cust);
	
	/**
	 * Update customer
	 * @param cust
	 */
	@Update("UPDATE customer C SET C.memo=#{memo}, C.status=#{status} WHERE C.cust_id=#{cust_id}")
	void updateCustomer(Customer cust);
	
	/**
	 * add customer service
	 * @param service
	 */
	@Insert("INSERT INTO customer_service(cust_id,service,status,expire_time) SELECT #{cust_id},#{service},#{status},#{expire_time}")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	void addService(CustomerService service);
	
	/**
	 * get customer service by id
	 * @param id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.id=#{id}")
	CustomerService getServiceById(int id);
	
	/**
	 * get customer service by service type
	 * @param cust_id
	 * @param service
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.cust_id=#{cust_id} AND A.service=#{service}")
	CustomerService getService(@Param("cust_id")int cust_id, @Param("service")ServiceType service);
	
	/**
	 * get all the services of the customer
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.cust_id=#{cust_id}")
	List<CustomerService> getServives(int cust_id);
}
