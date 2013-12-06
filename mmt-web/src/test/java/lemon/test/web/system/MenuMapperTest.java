package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

import lemon.test.web.base.BaseWebTest;
import lemon.web.system.bean.Menu;
import lemon.web.system.mapper.MenuMapper;
import lemon.web.system.mapper.RoleMapper;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//FIXME 修改MENU测试用例
public class MenuMapperTest extends BaseWebTest {
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMapper roleMapper;
	private static final String LEVEL1 = "11";
	private static final String LEVEL2 = "12";
	private static final String LEVEL3 = "13";
	
	@Test
	@Ignore
	public void deleteMenu(){
		Menu menu = addMenu(LEVEL2,1);
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
	
}
