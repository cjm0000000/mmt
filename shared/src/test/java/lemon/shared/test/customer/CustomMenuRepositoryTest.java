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
public class CustomMenuRepositoryTest {
	private static final byte LEVEL = 9;
	private static final int CUST_ID = -5743;
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
		assertNotEquals(0, customMenuMapper.addMenu(menu));
	}

	@Test
	public void update() {
		CustomMenu menu = getCustomMenu();
		assertNotEquals(0, customMenuMapper.addMenu(menu));
		String name = "NAME AFTER UPDATE";
		int sort = 300;
		byte menulevcod = 2;
		int supmenucode = 1;
		String type = "VIEW";
		menu.setName(name);
		menu.setSort(sort);
		menu.setMenulevcod(menulevcod);
		menu.setSupmenucode(supmenucode);
		menu.setType(type);
		assertNotEquals(0, customMenuMapper.editMenu(menu));
		
		menu = customMenuMapper.getMenu(menu.getMenu_id());
		assertEquals(name, menu.getName());
		assertEquals(sort, menu.getSort());
		assertEquals(menulevcod, menu.getMenulevcod());
		assertEquals(supmenucode, menu.getSupmenucode());
		assertEquals(type, menu.getType());
	}

	@Test
	public void batchDelete() {
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		assertNotEquals(0, customMenuMapper.addMenu(menu1));
		assertNotEquals(0, customMenuMapper.addMenu(menu2));
		assertNotEquals(0, customMenuMapper.addMenu(menu3));
		
		int result = customMenuMapper.deleteMenu(new String[] {
				String.valueOf(menu1.getMenu_id()),
				String.valueOf(menu2.getMenu_id()),
				String.valueOf(menu3.getMenu_id()) });
		assertNotEquals(0, result);
		
		menu1 = customMenuMapper.getMenu(menu1.getMenu_id());
		assertNull(menu1);
		
		menu2 = customMenuMapper.getMenu(menu2.getMenu_id());
		assertNull(menu2);
		
		menu3 = customMenuMapper.getMenu(menu3.getMenu_id());
		assertNull(menu3);
	}
	
	@Test
	public void getMenuList(){
		assertNotEquals(0, customMenuMapper.deleteMenuByCustomer(CUST_ID));
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		assertNotEquals(0, customMenuMapper.addMenu(menu1));
		assertNotEquals(0, customMenuMapper.addMenu(menu2));
		assertNotEquals(0, customMenuMapper.addMenu(menu3));
		
		List<CustomMenu> list = customMenuMapper.getMenuList(CUST_ID);
		assertNotNull(list);
		assertEquals(3, list.size());
	}
	
	@Test
	public void getMenuListByLevel(){
		assertNotEquals(0, customMenuMapper.deleteMenuByCustomer(CUST_ID));
		CustomMenu menu1 = getCustomMenu();
		CustomMenu menu2 = getCustomMenu();
		CustomMenu menu3 = getCustomMenu();
		assertNotEquals(0, customMenuMapper.addMenu(menu1));
		assertNotEquals(0, customMenuMapper.addMenu(menu2));
		assertNotEquals(0, customMenuMapper.addMenu(menu3));
		
		List<CustomMenu> list = customMenuMapper.getMenuListByLevel(CUST_ID, LEVEL);
		assertNotNull(list);
		assertEquals(3, list.size());
	}
	
	@Test
	public void saveMenuSyncLog(){
		CustomMenuLog log = new CustomMenuLog();
		log.setAccess_token("access_token");
		log.setAction(Action.DELETE);
		log.setCust_id(CUST_ID);
		log.setMsg("msg");
		log.setResult("result");
		log.setService_type(ServiceType.OTHER);
		log.setId(IdWorkerManager.getIdWorker(CustomMenuLog.class).getId());
		assertNotEquals(0, customMenuMapper.saveMenuSyncLog(log));
	}

	private CustomMenu getCustomMenu() {
		CustomMenu menu = new CustomMenu();
		menu.setCust_id(CUST_ID);
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
