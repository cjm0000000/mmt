package lemon.shared.customer.mapper;

import java.util.List;

import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;
import lemon.shared.service.ServiceType;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
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
	@Lang(RawLanguageDriver.class)
	Customer getCustomer(int cust_id);
	
	/**
	 * Get customer list
	 * @param start
	 * @param limit
	 * @return
	 */
	@Select("SELECT C.cust_id,C.cust_name,C.memo FROM customer C WHERE C.status='AVAILABLE' LIMIT #{start},#{limit}")
	@Lang(RawLanguageDriver.class)
	List<Customer> getCustomerList(@Param("start") int start, @Param("limit") int limit);
	
	/**
	 * Get customer count
	 * @return
	 */
	@Select("SELECT COUNT(1) FROM customer C WHERE C.status='AVAILABLE'")
	@Lang(RawLanguageDriver.class)
	int getCustCnt();
	
	/**
	 * Add Customer
	 * @param cust
	 */
	@Insert("INSERT INTO customer(cust_name,memo,status) SELECT #{cust_name},#{memo},#{status}")
	@Options(useGeneratedKeys=true,keyProperty="cust_id",keyColumn="cust_id")
	@Lang(RawLanguageDriver.class)
	int addCustomer(Customer cust);
	
	/**
	 * Update customer
	 * @param cust
	 */
	@Update("UPDATE customer C SET C.cust_name=#{cust_name}, C.memo=#{memo} WHERE C.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int updateCustomer(Customer cust);
	
	/**
	 * Delete customer
	 * @param cust_id
	 * @return
	 */
	@Update("UPDATE customer C SET  C.status='UNAVAILABLE' WHERE C.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int delete(int cust_id); 
	
	/**
	 * add customer service
	 * @param service
	 */
	@Insert("INSERT INTO customer_service(cust_id,service_type,status,expire_time) SELECT #{cust_id},#{service_type},#{status},#{expire_time}")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	@Lang(RawLanguageDriver.class)
	int addService(CustomerService service);
	
	/**
	 * get customer service by id
	 * @param id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service_type,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.id=#{id}")
	@Lang(RawLanguageDriver.class)
	CustomerService getServiceById(int id);
	
	/**
	 * get customer service by service type
	 * @param cust_id
	 * @param service
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service_type,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.cust_id=#{cust_id} AND A.service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	CustomerService getService(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
	
	/**
	 * get all the services of the customer
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.service_type,A.status,date_format(A.expire_time,'%Y-%m-%d %H:%i') expire_time FROM customer_service A WHERE A.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	List<CustomerService> getServices(int cust_id);
	
	/**
	 * delete customer's service by cust_id and service type
	 * @param cust_id
	 * @param service
	 * @return
	 */
	@Delete("DELETE FROM customer_service WHERE cust_id=#{cust_id} AND service_type=#{service_type}")
	@Lang(RawLanguageDriver.class)
	int deleteService(@Param("cust_id")int cust_id, @Param("service_type")ServiceType service_type);
}
