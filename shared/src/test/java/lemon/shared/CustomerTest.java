package lemon.shared;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.common.Customer;
import lemon.shared.mapper.CustomerMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class CustomerTest {
	
	private CustomerMapper custMapper;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		ApplicationContext acx = new ClassPathXmlApplicationContext(resource);
		custMapper = (CustomerMapper) acx.getBean(CustomerMapper.class);
		assertNotNull(custMapper);
	}
	
	@Test
	public void testCustomerMapper(){
		Customer cust = add();
		assertNotEquals(0, cust.getCust_id());
		String memo = "A&*HIO(@(@H(F@()))@JDWJ()!Q@";
		update(cust, memo);
		assertNotEquals(0, list().size());
	}
	
	private Customer add(){
		Customer cust = new Customer();
		cust.setCust_name("Insigma");
		cust.setMemo("MEMO...");
		cust.setStatus("1");
		custMapper.save(cust);
		return cust;
	}
	
	private void update(Customer cust,String memo){
		cust.setMemo(memo);
		custMapper.update(cust);
	}
	
	private List<Customer> list(){
		return custMapper.activeList();
	}

}
