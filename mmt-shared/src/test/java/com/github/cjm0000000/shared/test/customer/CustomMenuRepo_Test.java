package com.github.cjm0000000.shared.test.customer;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.customer.CustomMenu;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuLog;
import com.github.cjm0000000.mmt.shared.customer.persistence.CustomMenuRepository;
import com.github.cjm0000000.mmt.shared.toolkit.idcenter.IdWorkerManager;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test case for custom menu
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenuRepo_Test extends AbstractTester {
	private static final byte LEVEL = 9;
	@Autowired
	private CustomMenuRepository customMenuMapper;

	@Override
	protected void defaultCase() {
		addMenu();
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
		customMenuMapper.deleteMenuByCustomer(CUST_ID);
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
		log.setAccess_token("access_token" + UUID.randomUUID().toString());
		log.setAction(CustomMenuLog.Action.DELETE);
		log.setCust_id(CUST_ID);
		log.setMsg("msg: " + UUID.randomUUID().toString());
		log.setResult("result: " + UUID.randomUUID().toString());
		log.setService_type(ServiceType.OTHER);
		log.setId(IdWorkerManager.getIdWorker(CustomMenuLog.class).getId());
		assertNotEquals(0, customMenuMapper.saveMenuSyncLog(log));
	}
	
	/**
	 * add custom menu
	 */
	private void addMenu() {
		CustomMenu menu = getCustomMenu();
		assertNotEquals(0, customMenuMapper.addMenu(menu));
	}

	/**
	 * get custom menu
	 * @return
	 */
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
