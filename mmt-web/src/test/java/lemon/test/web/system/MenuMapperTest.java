package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.test.web.base.BaseWebTest;
import lemon.web.system.bean.Menu;
import lemon.web.system.mapper.MenuMapper;
import lemon.web.system.mapper.RoleMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuMapperTest extends BaseWebTest {
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMapper roleMapper;
	private static final String LEVEL1 = "1";
	private static final String LEVEL2 = "2";
	private static final String LEVEL3 = "3";
	private int root_menu_id;
	/**
	 * Initialize root menu
	 */
	@Before
	public void init(){
		List<Menu> list = menuMapper.getMenuListByLevel(LEVEL1);
		if(list == null || list.size() == 0){
			Menu root = addMenu(LEVEL1, 0);
			this.root_menu_id = root.getMenu_id();
		}else
			this.root_menu_id = list.get(0).getMenu_id();
	}
	
	@Test
	public void deleteMenu(){
		Menu menu = addMenu(LEVEL2, this.root_menu_id);
		assertNotNull(menu);
		int result = menuMapper.deleteMenu(new String[]{String.valueOf(menu.getMenu_id())});
		assertNotEquals(0, result);
		menu = menuMapper.getMenu(menu.getMenu_id());
		assertNull(menu);
	}
	
	@Test
	public void editMenu(){
		Menu menu = addMenu(LEVEL2, this.root_menu_id);
		String name_after_update = "Menu After Update";
		int supmenucode = 100101;
		String level = LEVEL3;
		String icon = "glyphicon glyphicon-user";
		menu.setMenu_name(name_after_update);
		menu.setMenulevcod(level);
		menu.setSupmenucode(supmenucode);
		menu.setMenuico(icon);
		assertNotEquals(0, menuMapper.editMenu(menu));
		menu = menuMapper.getMenu(menu.getMenu_id());
		assertEquals(name_after_update, menu.getMenu_name());
		assertEquals(supmenucode, menu.getSupmenucode());
		assertEquals(level, menu.getMenulevcod());
		assertEquals(icon, menu.getMenuico());
	}
	
	@Test
	public void getMenuList(){
		addMenu(LEVEL2, root_menu_id);
		List<Menu> list = menuMapper.getMenuList();
		assertNotNull(list);
		assertTrue(list.size() >= 1);
		for (Menu menu : list) {
			System.out.println(menu.getMenu_name());
		}
	}
	
	@Test
	public void getMenuListByLevel(){
		deleteMenuByLevel(LEVEL2);
		deleteMenuByLevel(LEVEL3);
		Menu m1 = addMenu(LEVEL2, root_menu_id);
		addMenu(LEVEL3, m1.getMenu_id());
		addMenu(LEVEL3, m1.getMenu_id());
		List<Menu> l2 = menuMapper.getMenuListByLevel(LEVEL2);
		assertEquals(1, l2.size());
		List<Menu> l3 = menuMapper.getMenuListByLevel(LEVEL3);
		assertEquals(2, l3.size());
	}
	
	private Menu addMenu(String lev, int parent_id){
		Menu menu = new Menu();
		menu.setMenu_name("测试菜单" + parent_id);
		menu.setMenuico("ico icon124");
		menu.setMenulevcod(lev);
		menu.setMenuurl("system/test");
		menu.setSort(1);
		menu.setSupmenucode(parent_id);
		assertNotEquals(0, menuMapper.addMenu(menu));
		return menu;
	}
	
	private void deleteMenuByLevel(String level){
		List<Menu> list = menuMapper.getMenuListByLevel(level);
		if(list == null || list.size() == 0)
			return;
		String[] menuIds = new String[list.size()];
		for (int i = 0; i < list.size(); i++) 
			menuIds[i] = String.valueOf(list.get(i).getMenu_id());
		assertNotEquals(0, menuMapper.deleteMenu(menuIds));
	}
	
}
