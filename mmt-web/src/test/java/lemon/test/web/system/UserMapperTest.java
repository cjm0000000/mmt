package lemon.test.web.system;

import static org.junit.Assert.assertNotNull;
import lemon.shared.entity.Status;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.UserMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class UserMapperTest {
	private ApplicationContext acx;
	private UserMapper userMapper;
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		userMapper = acx.getBean(UserMapper.class);
		assertNotNull(userMapper);
	}
	@Test
	public void addUser(){
		User user = new User();
		user.setBz("备注");
		user.setIslock(Status.UNAVAILABLE);
		user.setPassword("password");
		//TODO set user role ID
		user.setRole_id(10);
	}
}
