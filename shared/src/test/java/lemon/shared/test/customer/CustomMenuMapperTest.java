package lemon.shared.test.customer;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.customer.Action;
import lemon.shared.customer.CustomMenu;
import lemon.shared.customer.log.CustomMenuLog;
import lemon.shared.customer.mapper.CustomMenuMapper;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class CustomMenuMapperTest {
	private static final byte LEVEL = 9;
	private ApplicationContext acx;
	private CustomMenuMapper customMenuMapper;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		customMenuMapper = acx.getBean(CustomMenuMapper.class);
		assertNotNull(customMenuMapper);
	}

	@Test
	public void add() {
		CustomMenu menu = getCustomMenu();
		customMenuMapper.addMenu(menu);
		assertNotEquals(0, menu.getMenu_id());
		customMenuMapper.deleteMenu(new String[] { String.valueOf(menu
				.getMenu_id()) });
		assertNull(customMenuMapper.getMenu(menu.getMenu_id()));
	}

	@Test
	public void update() {
		CustomMenu menu = getCustomMenu();
		customMenuMapper.addMenu(menu);
		String name = "AFTER UPDATE";
		int sort = 300;
		byte menulevcod = 2;
		int supmenucode = 1;
		String type = "NEWT";
		menu.setName(name);
		menu.setSort(sort);
		menu.setMenulevcod(menulevcod);
		menu.setSupmenucode(supmenucode);
		menu.setType(type);
		customMenuMapper.editMenu(menu);
		menu = customMenuMapper.getMenu(menu.getMenu_id());
		assertEquals(name, menu.getName());
		assertEquals(sort, menu.getSort());
		assertEquals(menulevcod, menu.getMenulevcod());
		assertEquals(supmenucode, menu.getSupmenucode());
		assertEquals(type, menu.getType());
		customMenuMapper.deleteMenu(new String[] { String.valueOf(menu
				.getMenu_id()) });
	}

	@Test
	public void batchDelete() {
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		customMenuMapper.addMenu(menu1);
		customMenuMapper.addMenu(menu2);
		customMenuMapper.addMenu(menu3);
		customMenuMapper.deleteMenu(new String[] {
				String.valueOf(menu1.getMenu_id()),
				String.valueOf(menu2.getMenu_id()),
				String.valueOf(menu3.getMenu_id()) });
		menu1 = customMenuMapper.getMenu(menu1.getMenu_id());
		menu2 = customMenuMapper.getMenu(menu2.getMenu_id());
		menu3 = customMenuMapper.getMenu(menu3.getMenu_id());
		assertNull(menu1);
		assertNull(menu2);
		assertNull(menu3);
	}
	
	@Test
	public void getMenuList(){
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		customMenuMapper.addMenu(menu1);
		customMenuMapper.addMenu(menu2);
		customMenuMapper.addMenu(menu3);
		List<CustomMenu> list = customMenuMapper.getMenuList(-1);
		assertNotNull(list);
		assertEquals(3, list.size());
		customMenuMapper.deleteMenu(new String[] {
				String.valueOf(menu1.getMenu_id()),
				String.valueOf(menu2.getMenu_id()),
				String.valueOf(menu3.getMenu_id()) });
	}
	
	@Test
	public void getMenuListByLevel(){
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		customMenuMapper.addMenu(menu1);
		customMenuMapper.addMenu(menu2);
		customMenuMapper.addMenu(menu3);
		List<CustomMenu> list = customMenuMapper.getMenuListByLevel(-1, LEVEL);
		assertNotNull(list);
		assertEquals(3, list.size());
		customMenuMapper.deleteMenu(new String[] {
				String.valueOf(menu1.getMenu_id()),
				String.valueOf(menu2.getMenu_id()),
				String.valueOf(menu3.getMenu_id()) });
	}
	
	@Test
	public void saveCustomMenuLog(){
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token("accesstoken");
		log.setAction(Action.DELETE);
		log.setCust_id(1);
		log.setMsg("msg");
		log.setResult("result");
		log.setService_type(ServiceType.OTHER);
		log.setId(IdWorkerManager.getIdWorker(CustomMenuLog.class).getId());
		customMenuMapper.saveMenuSyncLog(log);
		assertNotEquals(0, log.getId());
	}

	private CustomMenu getCustomMenu() {
		CustomMenu menu = new CustomMenu();
		menu.setCust_id(-1);
		menu.setKey(UUID.randomUUID().toString());
		menu.setMenulevcod(LEVEL);
		menu.setName("Test");
		menu.setSort(1);
		menu.setSupmenucode(0);
		menu.setType("view");
		menu.setMenu_id(IdWorkerManager.getIdWorker(CustomMenu.class).getId());
		return menu;
	}
}
