package lemon.shared.test.customer;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.CustomerService;
import lemon.shared.customer.mapper.CustomerMapper;
import lemon.shared.service.ServiceType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class CustomerTest {
	private AbstractApplicationContext acx;
	private CustomerMapper custMapper;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		custMapper = (CustomerMapper) acx.getBean(CustomerMapper.class);
		assertNotNull(custMapper);
	}
	@After
	public void destory(){
		acx.close();
	}
	
	@Test
	public void upd(){
		//Test(String), 331(String), 25(Integer)
		Customer cust = new Customer();
		cust.setCust_name("Test");
		cust.setMemo("331");
		cust.setCust_id(25);
		int result = custMapper.updateCustomer(cust);
		System.out.println(result);
	}
	
	@Test
	public void testCustomerBusiness(){
		Customer cust = addCustomer();
		assertNotEquals(0, cust.getCust_id());
		String memo = "A&*HIO(@(@H(F@()))@JDWJ()!Q@";
		String name = "name1";
		updateCustomer(cust, name, memo);
		assertNotEquals(0, list().size());
		cust = custMapper.getCustomer(cust.getCust_id());
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
		
		int result = custMapper.delete(cust.getCust_id());
		assertNotEquals(0, result);
	}
	
	private CustomerService addService(int cust_id){
		CustomerService service = new CustomerService();
		service.setCust_id(cust_id);
		service.setService_type(ServiceType.WEIXIN);
		service.setExpire_time("0000-00-00 00:00");
		service.setStatus(Status.AVAILABLE);
		int result = custMapper.addService(service);
		assertNotEquals(0, result);
		return service;
	}
	
	private Customer addCustomer(){
		Customer cust = new Customer();
		cust.setCust_name("LEMON TEST CUSTOMER");
		cust.setMemo("MEMO...");
		cust.setStatus(Status.AVAILABLE);
		int result = custMapper.addCustomer(cust);
		assertNotEquals(0, result);
		return cust;
	}
	
	private void updateCustomer(Customer cust,String name, String memo){
		cust.setCust_name(name);
		cust.setMemo(memo);
		int result = custMapper.updateCustomer(cust);
		System.out.println("UPD: "+result);
		assertNotEquals(0, result);
	}
	
	private List<Customer> list(){
		return custMapper.getCustomerList(0, 10);
	}

}
