package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.entity.Status;
import lemon.web.system.bean.Role;
import lemon.web.system.mapper.RoleMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RoleMapperTest {
	
	private ApplicationContext acx;
	private RoleMapper roleMapper;
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		roleMapper = acx.getBean(RoleMapper.class);
		assertNotNull(roleMapper);
	}
	
	@Test
	public void addRole(){
		Role role = new Role();
		role.setReloadable(Status.AVAILABLE);
		role.setRole_desc("role_desc");
		role.setRole_name("E1");
		role.setSort(1);
		role.setStatus(Status.AVAILABLE);
		roleMapper.addRole(role);
		assertNotEquals(0, role.getRole_id());
	}
	
	@Test
	public void roleList(){
		List<Role> list = roleMapper.getRoleList(0,0);
		for (Role role : list) {
			System.out.println(role.getRole_id());
		}
	}
}
