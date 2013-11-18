package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.config.Status;
import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.web.system.bean.Role;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.mapper.UserMapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class UserMapperTest {
	private ApplicationContext acx;
	private UserMapper userMapper;
	private RoleMapper roleMapper;
	private CustomerRepository customerMapper;
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		userMapper = acx.getBean(UserMapper.class);
		roleMapper = acx.getBean(RoleMapper.class);
		customerMapper = acx.getBean(CustomerRepository.class);
		assertNotNull(userMapper);
		assertNotNull(roleMapper);
		assertNotNull(customerMapper);
	}
	
	@Test
	@Ignore
	public void testUser(){
		User user = addUser();
		assertNotEquals(0, user.getUser_id());
		Role role = addRole();
		assertNotEquals(0, role.getRole_id());
		Customer cust = addCustomer();
		assertNotEquals(0, cust.getCust_id());
		userMapper.addUserRole(user.getUser_id(), role.getRole_id(), cust.getCust_id());
		//test check login
		User u3 = userMapper.checkLogin(user.getUsername(), user.getPassword());
		assertNotNull(u3);
		assertEquals(role.getRole_name(), u3.getRole_name());
		assertEquals(cust.getCust_name(), u3.getCust_name());
		//update role
		Role r2 = addRole();
		Customer c2 = addCustomer();
		userMapper.updateUserRole(user.getUser_id(), r2.getRole_id(), c2.getCust_id());
		User u4 = userMapper.getUserById(user.getUser_id());
		assertEquals(r2.getRole_name(), u4.getRole_name());
		assertEquals(c2.getCust_name(), u4.getCust_name());
	}
	
	@Test
	@Ignore
	public void getUserIdByName(){
		User u1 = addUser();
		assertNotNull(u1);
		int user_id = userMapper.getUserIdByName(u1.getUsername());
		assertEquals(user_id, u1.getUser_id());
	}
	
	@Test
	@Ignore
	public void getUserById(){
		User u1 = addUser();
		assertNotNull(u1);
		User u2 = userMapper.getUserById(u1.getUser_id());
		assertNotNull(u2);
	}
	
	@Test
	@Ignore
	public void deleteUser(){
		User u1 = addUser();
		User u2 = addUser();
		User u3 = addUser();
		String[] users = {String.valueOf(u1.getUser_id()),
					String.valueOf(u2.getUser_id()),
					String.valueOf(u3.getUser_id())};
		userMapper.deleteUser(users);
	}
	
	@Test
	public void getUserList(){
		List<User> list = userMapper.getUserList(0, 0,null);
		int cnt = userMapper.getUserCnt(null);
		assertEquals(cnt, list.size());
	}
	
	private User addUser(){
		User user = new User();
		user.setBz("备注");
		user.setIslock(Status.UNAVAILABLE);
		user.setPassword("password");
		user.setRole_id(10);
		user.setIdcard("330111190012123333");
		user.setMphone("18805718888");
		user.setStatus(Status.AVAILABLE);
		user.setUsername(new Object().toString());
		user.setXm("xm");
		userMapper.addUser(user);
		return user;
	}
	
	private Role addRole(){
		Role role = new Role();
		role.setReloadable(Status.AVAILABLE);
		role.setRole_desc("role_desc");
		role.setRole_name("ROLE_NAME");
		role.setSort(1);
		role.setStatus(Status.AVAILABLE);
		roleMapper.addRole(role);
		return role;
	}
	
	private Customer addCustomer(){
		Customer cust = new Customer();
		cust.setCust_name("LEMON TEST CUSTOMER");
		cust.setMemo("MEMO...");
		cust.setStatus(Status.AVAILABLE);
		customerMapper.addCustomer(cust);
		return cust;
	}
	
}
