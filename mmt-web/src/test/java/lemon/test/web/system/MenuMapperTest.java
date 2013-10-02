package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.shared.entity.Status;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;
import lemon.web.system.mapper.MenuMapper;
import lemon.web.system.mapper.RoleMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MenuMapperTest {
	private AbstractApplicationContext acx;
	private MenuMapper menuMapper;
	private RoleMapper roleMapper;
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		menuMapper = acx.getBean(MenuMapper.class);
		roleMapper = acx.getBean(RoleMapper.class);
		assertNotNull(menuMapper);
		assertNotNull(roleMapper);
//		List<Menu> list = menuMapper.getMenuList();
//		for (Menu menu : list) {
//			if(menu.getMenu_id() != 1)
//				menuMapper.deleteMenu(menu.getMenu_id());
//		}
	}
	
	@After
	public void destory(){
//		List<Menu> list = menuMapper.getMenuList();
//		for (Menu menu : list) {
//			if(menu.getMenu_id() != 1)
//				menuMapper.deleteMenu(menu.getMenu_id());
//		}
		acx.close();
	}
	
	@Test
	@Ignore
	public void deleteMenu(){
		Menu menu = addMenu("2",1);
		assertNotNull(menu);
		assertNotEquals(0, menu.getMenu_id());
		menuMapper.deleteMenu(new String[]{"2"});
		menu = menuMapper.getMenu(menu.getMenu_id());
		assertNull(menu);
	}
	
	@Test
	@Ignore
	public void editMenu(){
		Menu menu = addMenu("2", 1);
		menu.setMenu_name("");
		menu.setMenulevcod("");
		menu.setSupmenucode(10);
		menu.setMenuico("");
		menuMapper.editMenu(menu);
		menu  = menuMapper.getMenu(menu.getMenu_id());
		assertEquals(10, menu.getSupmenucode());
		assertEquals("menu-lev-2", menu.getMenu_name());
	}
	
	@Test
	@Ignore
	public void getMenuList(){
		List<Menu> list = menuMapper.getMenuList();
		assertNotNull(list);
		for (Menu menu : list) {
			System.out.println(menu.getMenu_name());
		}
	}
	
	@Test
	@Ignore
	public void getMenuListByLevel(){
		Menu m1 = addMenu("2", 1);
		addMenu("3", m1.getMenu_id());
		addMenu("3", m1.getMenu_id());
		List<Menu> l2 = menuMapper.getMenuListByLevel("2");
		assertEquals(1, l2.size());
		List<Menu> l3 = menuMapper.getMenuListByLevel("3");
		assertEquals(2, l3.size());
	}
	
	private Menu addMenu(String lev, int parent_id){
		Menu menu = new Menu();
		menu.setMenu_name("menu-lev-"+lev);
		menu.setMenuico("ico");
		menu.setMenulevcod(lev);
		menu.setMenuurl("url");
		menu.setSort(1);
		menu.setSupmenucode(parent_id);
		menuMapper.addMenu(menu);
		return menu;
	}
	
	private Role addRole(){
		Role role = new Role();
		role.setReloadable(Status.AVAILABLE);
		role.setRole_desc("role_desc");
		role.setRole_name("ROLE_NAME!##$");
		role.setSort(1);
		role.setStatus(Status.AVAILABLE);
		roleMapper.addRole(role);
		return role;
	}
	
}
