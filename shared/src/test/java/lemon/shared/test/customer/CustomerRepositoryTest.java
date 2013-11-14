package lemon.shared.test.customer;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class CustomerRepositoryTest {
	private ApplicationContext acx;
	private CustomerMapper custMapper;
	private int CUST_ID = -5743;
	 
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		custMapper = acx.getBean(CustomerMapper.class);
		assertNotNull(custMapper);
	}
	
	/**
	 * customer CRUD test
	 */
	@Test
	public void customerCRUD(){
		// Add
		Customer cust = addCustomer();
		assertNotEquals(0, cust.getCust_id());
		// Update
		String memo = UUID.randomUUID().toString();
		String name = "CUSTOMER By JUnit 4 Modified";
		updateCustomer(cust, name, memo);
		cust = custMapper.getCustomer(CUST_ID);
		assertEquals(name, cust.getCust_name());
		assertEquals(memo, cust.getMemo());
		//Select
		assertNotEquals(0, list().size());
		//Delete
		assertNotEquals(0,custMapper.delete(CUST_ID));
		cust = custMapper.getCustomer(CUST_ID);
		assertEquals(cust.getStatus(), Status.UNAVAILABLE);
	}
	
	@Test
	public void customerService(){
		Customer cust = addCustomer();
		CustomerService service = addService(cust.getCust_id());
		assertNotNull(service);
		assertEquals(service.getExpire_time(), "0000-00-00 00:00");
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
	
	private CustomerService addService(int cust_id){
		CustomerService service = new CustomerService();
		service.setCust_id(cust_id);
		service.setService_type(ServiceType.WEIXIN);
		service.setExpire_time("0000-00-00 00:00");
		service.setStatus(Status.AVAILABLE);
		service.setId(IdWorkerManager.getIdWorker(CustomerService.class).getId());
		int result = custMapper.addService(service);
		assertNotEquals(0, result);
		return service;
	}
	
	private Customer addCustomer(){
		Customer cust = new Customer();
		cust.setCust_name("CUSTOMER By JUnit 4");
		cust.setMemo(UUID.randomUUID().toString());
		cust.setStatus(Status.AVAILABLE);
		int result = custMapper.addCustomer(cust);
		assertNotEquals(0, result);
		this.CUST_ID = cust.getCust_id();
		return cust;
	}
	
	private void updateCustomer(Customer cust,String name, String memo){
		cust.setCust_name(name);
		cust.setMemo(memo);
		int result = custMapper.updateCustomer(cust);
		assertNotEquals(0, result);
	}
	
	private List<Customer> list(){
		return custMapper.getCustomerList(0, 10);
	}

}
