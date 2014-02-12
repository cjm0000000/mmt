package com.github.cjm0000000.mmt.shared.customer.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.shared.customer.CustomMenu;
import com.github.cjm0000000.mmt.shared.customer.CustomMenuLog;


/**
 * custom menu repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface CustomMenuRepository {
	
	/**
	 * add menu
	 * @param menu
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addMenu(CustomMenu menu);
	
	/**
	 * Batch delete menu by id
	 * @param menuIds
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int deleteMenu(String[] menuIds);
	
	/**
	 * Delete menu by customer id
	 * @param cust_id
	 * @return
	 */
	int deleteMenuByCustomer(int cust_id);
	
	/**
	 * edit menu
	 * @param menu
	 * @return
	 */
	int editMenu(CustomMenu menu);
	
	/**
	 * get menu by menu id
	 * @param menu_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	CustomMenu getMenu(long menu_id);
	
	/**
	 * get menu by key and id
	 * @param cust_id
	 * @param key
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	CustomMenu getMenuByKey(@Param("cust_id") int cust_id,
			@Param("key") String key);
	
	/**
	 * get all menu list by customer id
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<CustomMenu> getMenuList(int cust_id);
	
	/**
	 * get menu list by menu level
	 * @param cust_id
	 * @param level
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<CustomMenu> getMenuListByLevel(@Param("cust_id") int cust_id,
			@Param("level") byte level);
	
	/**
	 * save custom menu log
	 * @param log
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int saveMenuSyncLog(CustomMenuLog log);
}
