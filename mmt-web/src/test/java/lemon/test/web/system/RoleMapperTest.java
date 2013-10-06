package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.entity.Status;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;
import lemon.web.system.mapper.RoleMapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class RoleMapperTest {
	private ApplicationContext acx;
	private RoleMapper roleMapper;
	private final String role_name = "R123456R";
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		roleMapper = acx.getBean(RoleMapper.class);
		assertNotNull(roleMapper);
	}
	
	@Test
	@Ignore
	public void testAddRole(){
		Role role = addRole();
		assertNotEquals(0, role.getRole_id());
		assertNotEquals(0, roleMapper.getRsCnt());
		role.setRole_desc("desc123");
		role.setSort(3);
		role.setReloadable(Status.UNAVAILABLE);
		roleMapper.update(role);
		role = roleMapper.getRole(role.getRole_id());
		assertEquals(role.getReloadable(), Status.UNAVAILABLE);
		//clean
		roleMapper.batchDelete(new String[]{String.valueOf(role.getRole_id())});
	}
	
	@Test
	@Ignore
	public void roleList(){
		List<Role> list = roleMapper.getRoleList(0,5);
		for (Role role : list) {
			System.out.println(role.getRole_id());
		}
	}
	@Test
	@Ignore
	public void testCheckRoleName(){
		Role r1 = addRole();
		int matchers = roleMapper.checkRoleName(r1.getRole_name());
		assertNotEquals(0, matchers);
		//clean
		roleMapper.batchDelete(new String[]{String.valueOf(r1.getRole_id())});
	}
	@Test
	@Ignore
	public void testBatchDelete(){
		Role r1 = addRole();
		Role r2 = addRole();
		roleMapper.batchDelete(new String[]{r1.getRole_id()+"",r2.getRole_id()+""});
	}
	
	@Test
	@Ignore
	public void testoleAuthority(){
		Role role = addRole();
		String[] menus = {"1","2"};
		roleMapper.setRoleAuthority(role.getRole_id(),menus);
		
		roleMapper.deleteRoleAuthority(role.getRole_id());
	}
	
	@Test
	@Ignore
	public void testGetAuthority(){
		Role role = addRole();
		String[] menus = {"1","2"};
		roleMapper.setRoleAuthority(role.getRole_id(),menus);
		List<Menu> list = roleMapper.getAuthority(role.getRole_id());
		assertNotEquals(0, list.size());
	}
	
	@Test
	@Ignore
	public void setReloadFlag(){
		roleMapper.setReloadFlag(1);
	}
	
	private Role addRole(){
		Role role = new Role();
		role.setReloadable(Status.AVAILABLE);
		role.setRole_desc("role_desc");
		role.setRole_name(role_name);
		role.setSort(1);
		role.setStatus(Status.AVAILABLE);
		roleMapper.addRole(role);
		return role;
	}
}
