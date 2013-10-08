package lemon.shared.customer.mapper;

import java.util.List;

import lemon.shared.customer.bean.Customer;
import lemon.shared.customer.bean.CustomerService;
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
	 * Get customer list
	 * @param start
	 * @param limit
	 * @return
	 */
	@Select("SELECT C.cust_id,C.cust_name,C.memo FROM customer C WHERE C.status='AVAILABLE' LIMIT #{start},#{limit}")
	List<Customer> getCustomerList(@Param("start") int start,
			@Param("limit") int limit);
	
	/**
	 * Get customer count
	 * @return
	 */
	@Select("SELECT COUNT(1) FROM customer C WHERE C.status='AVAILABLE'")
	int getCustCnt();
	
	/**
	 * Add Customer
	 * @param cust
	 */
	@Insert("INSERT INTO customer(cust_name,memo,status) SELECT #{cust_name},#{memo},#{status}")
	@Options(useGeneratedKeys=true,keyProperty="cust_id",keyColumn="cust_id")
	int addCustomer(Customer cust);
	
	/**
	 * Update customer
	 * @param cust
	 */
	@Update("UPDATE customer C SET C.cust_name=#{cust_name}, C.memo=#{memo} WHERE C.cust_id=#{cust_id}")
	int updateCustomer(Customer cust);
	
	/**
	 * Delete customer
	 * @param cust_id
	 * @return
	 */
	@Update("UPDATE customer C SET  C.status='UNAVAILABLE' WHERE C.cust_id=#{cust_id}")
	int delete(int cust_id); 
	
	/**
	 * add customer service
	 * @param service
	 */
	@Insert("INSERT INTO customer_service(cust_id,service,status,expire_time) SELECT #{cust_id},#{service},#{status},#{expire_time}")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	int addService(CustomerService service);
	
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
	List<CustomerService> getServices(int cust_id);
}
