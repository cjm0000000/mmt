package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.Menu;


/**
 * system menu repository
 * 
 * @author lemon
 * @date Mar 17, 2012 3:02:09 PM
 * @version 1.0
 * 
 */
@Repository
public interface MenuRepository {
	
	/**
	 * add menu
	 * @param menu
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addMenu(Menu menu);
	
	/**
	 * Batch delete menu by id
	 * @param menuIds
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int deleteMenu(String[] menuIds);
	
	/**
	 * edit menu
	 * @param menu
	 * @return
	 */
	int editMenu(Menu menu);
	
	/**
	 * get menu by menu id
	 * @param menu_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	Menu getMenu(int menu_id);
	
	/**
	 * get all menu list
	 * 
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Menu> getMenuList();
	
	/**
	 * get menu list by menu level
	 * @param level
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Menu> getMenuListByLevel(String level);
	
	/**
	 * get menu list by parent menu
	 * @param parent_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Menu> getMenuListByParent(int parent_id);
	
	/**
	 * get parent menu list
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Menu> getParentMenuList();
	
}
