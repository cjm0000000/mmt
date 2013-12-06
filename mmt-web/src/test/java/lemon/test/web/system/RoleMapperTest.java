package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.config.Status;
import lemon.test.web.base.BaseWebTest;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;
import lemon.web.system.mapper.RoleMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleMapperTest extends BaseWebTest {
	@Autowired
	private RoleMapper roleMapper;
	private final String role_name = "R123456R";
	
	@Test
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
	public void roleList(){
		List<Role> list = roleMapper.getRoleList(0,5);
		for (Role role : list) {
			System.out.println(role.getRole_id());
		}
	}
	
	@Test
	public void testCheckRoleName(){
		Role r1 = addRole();
		int matchers = roleMapper.checkRoleName(r1.getRole_name());
		assertNotEquals(0, matchers);
		//clean
		roleMapper.batchDelete(new String[]{String.valueOf(r1.getRole_id())});
	}
	@Test
	public void testBatchDelete(){
		Role r1 = addRole();
		Role r2 = addRole();
		roleMapper.batchDelete(new String[]{r1.getRole_id()+"",r2.getRole_id()+""});
	}
	
	@Test
	public void testoleAuthority(){
		Role role = addRole();
		String[] menus = {"1","2"};
		roleMapper.setRoleAuthority(role.getRole_id(),menus);
		
		roleMapper.deleteRoleAuthority(role.getRole_id());
	}
	
	@Test
	public void testGetAuthority(){
		Role role = addRole();
		String[] menus = {"1","2"};
		roleMapper.setRoleAuthority(role.getRole_id(),menus);
		List<Menu> list = roleMapper.getAuthority(role.getRole_id());
		assertNotEquals(0, list.size());
	}
	
	@Test
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
