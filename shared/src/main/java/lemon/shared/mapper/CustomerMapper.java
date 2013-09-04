package lemon.shared.mapper;

import java.util.List;

import lemon.shared.common.Customer;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Customer mapper
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
	public Customer get(int cust_id);
	/**
	 * Get all active customer
	 * @return
	 */
	@Select("SELECT C.cust_id,C.cust_name,C.memo FROM customer C WHERE C.status='1'")
	public List<Customer> activeList();
	/**
	 * Save Customer
	 * @param cust
	 */
	@Insert("INSERT INTO customer(cust_name,memo,status) SELECT #{cust_name},#{memo},#{status}")
	@Options(useGeneratedKeys=true,keyProperty="cust_id",keyColumn="cust_id")
	public void save(Customer cust);
	
	/**
	 * Update customer
	 * @param cust
	 */
	@Update("UPDATE customer C SET C.memo=#{memo} WHERE C.cust_id=#{cust_id}")
	public void update(Customer cust);
	
	//public void delete(int cust_id);
}
