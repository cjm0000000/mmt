package lemon.shared.test.customer;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.test.base.BaseMmtTest;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRepositoryTest extends BaseMmtTest {
	@Autowired
	private CustomerRepository custMapper;
	private int CUST_ID_ = -5743;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	 
	/**
	 * customer CRUD test
	 */
	@Test
	public void customerCRUD(){
		// Add
		Customer cust = addCustomer(UUID.randomUUID().toString());
		assertNotEquals(0, cust.getCust_id());
		// Update
		String memo = UUID.randomUUID().toString();
		String name = "CUSTOMER By JUnit 4 Modified";
		updateCustomer(cust, name, memo);
		cust = custMapper.getCustomer(CUST_ID_);
		assertEquals(name, cust.getCust_name());
		assertEquals(memo, cust.getMemo());
		//Select
		assertNotEquals(0, list().size());
		//Delete
		assertNotEquals(0,custMapper.delete(CUST_ID_));
		cust = custMapper.getCustomer(CUST_ID_);
		assertEquals(cust.getStatus(), Status.UNAVAILABLE);
	}
	
	@Test
	public void customerService(){
		Customer cust = addCustomer(UUID.randomUUID().toString());
		CustomerService service = addService(cust.getCust_id());
		assertNotNull(service);
		assertEquals(SDF.format(new Date()), SDF.format(service.getExpire_time()));
		assertEquals(service.getStatus(), Status.AVAILABLE);
		
		CustomerService s1 = custMapper.getServiceById(service.getId());
		assertNotNull(s1);
		assertEquals(s1.getStatus(), Status.AVAILABLE);
		assertEquals(s1.getService_type(), ServiceType.WEIXIN);
		
		CustomerService s2 = custMapper.getService(cust.getCust_id(), ServiceType.WEIXIN);
		assertNotNull(s2);
		assertEquals(s2.getStatus(), Status.AVAILABLE);
		assertEquals(s2.getService_type(), ServiceType.WEIXIN);
		
		CustomerService s3 = custMapper.getService(cust.getCust_id(), ServiceType.OTHER);
		assertNull(s3);
		
		List<CustomerService> list = custMapper.getServices(cust.getCust_id());
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void listByName(){
		String cust_name = UUID.randomUUID().toString();
		addCustomer(cust_name);
		List<Customer> list = custMapper.getCustomerList(0, -1, cust_name);
		assertNotNull(list);
		assertEquals(cust_name, list.get(0).getCust_name());
		int cnt = custMapper.getCustCnt(cust_name);
		assertEquals(cnt, list.size());
	}
	
	private CustomerService addService(int cust_id){
		CustomerService service = new CustomerService();
		service.setCust_id(cust_id);
		service.setService_type(ServiceType.WEIXIN);
		service.setExpire_time(new Date());
		service.setStatus(Status.AVAILABLE);
		service.setId(IdWorkerManager.getIdWorker(CustomerService.class).getId());
		int result = custMapper.addService(service);
		assertNotEquals(0, result);
		return service;
	}
	
	private Customer addCustomer(String cust_name){
		Customer cust = new Customer();
		cust.setCust_name(cust_name);
		cust.setMemo(UUID.randomUUID().toString());
		cust.setStatus(Status.AVAILABLE);
		int result = custMapper.addCustomer(cust);
		assertNotEquals(0, result);
		this.CUST_ID_ = cust.getCust_id();
		return cust;
	}
	
	private void updateCustomer(Customer cust,String name, String memo){
		cust.setCust_name(name);
		cust.setMemo(memo);
		int result = custMapper.updateCustomer(cust);
		assertNotEquals(0, result);
	}
	
	private List<Customer> list(){
		return custMapper.getCustomerList(0, 10, null);
	}

}
