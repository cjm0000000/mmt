package com.github.cjm0000000.mmt.web.test.system;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.web.system.bean.Menu;
import com.github.cjm0000000.mmt.web.system.persistence.RoleMenuMapper;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;

public class RoleMenuMapperTest extends AbstractWebTester {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Test
	@Ignore
	public void getLeafMenuByUrl(){
		String url = "menu";
		String superUrl = "system";
		Menu menu = roleMenuMapper.getLeafMenuByUrl(url, superUrl,1);
		assertNotNull(menu);
		assertEquals(7, menu.getMenu_id());
	}
	
	@Test
	@Ignore
	public void getSecondLevelMenuByUrl(){
		String url = "system";
		Menu menu = roleMenuMapper.getSecondLevelMenuByUrl(url,1);
		assertNotNull(menu);
		assertEquals(2, menu.getMenu_id());
	}
	
	@Test
	@Ignore
	public void getDefaultChild(){
		int menu_id = 2;
		Menu menu = roleMenuMapper.getDefaultChild(menu_id,1);
		assertNotNull(menu);
		assertEquals(5, menu.getMenu_id());
	}
	
	@Test
	@Ignore
	public void getMenuListByRole(){
		List<Menu> l2 = roleMenuMapper.getMenuListByRole(1,"3");
		assertNotNull(l2);
		assertEquals(0, l2.size());
	}
	
}
