package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.Menu;

/**
 * Menu by role repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface RoleMenuMapper {
	/**
	 * get third menu by menu URL and super menu URL
	 * @param url
	 * @param superUrl
	 * @param role_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT A.menu_id,A.menu_name,A.menulevcod,A.supmenucode,A.menuurl,A.menuico,A.sort " +
				"FROM system_menu A, system_role_menu C " +
			"WHERE A.menu_id=C.menu_id " +
				"AND A.menuurl=#{url} " +
				"AND C.role_id=#{role_id} " +
				"AND EXISTS(SELECT 1 FROM system_menu B WHERE B.menuurl=#{superUrl} AND A.supmenucode=B.menu_id)")
	Menu getLeafMenuByUrl(@Param("url") String url,
			@Param("superUrl") String superUrl, @Param("role_id") int role_id);
	
	/**
	 * get second menu by menu URL
	 * @param menuurl
	 * @param role_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT A.menu_id,A.menu_name,A.menulevcod,A.supmenucode,A.menuurl,A.menuico,A.sort " +
				"FROM system_menu A, system_role_menu B " +
			"WHERE A.menu_id=B.menu_id " +
				"AND A.menulevcod='2' " +
				"AND B.role_id=#{role_id} " +
				"AND A.menuurl=#{menuurl}")
	Menu getSecondLevelMenuByUrl(@Param("menuurl") String menuurl,
			@Param("role_id") int role_id);


	/**
	 * get default child
	 * @param supmenucode
	 * @param role_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT A.menu_id,A.menu_name,A.menulevcod,A.supmenucode,A.menuurl,A.menuico,A.sort FROM system_menu A, system_role_menu B WHERE A.menu_id=B.menu_id AND B.role_id=#{role_id} AND A.supmenucode=#{supmenucode} ORDER BY A.sort LIMIT 1")
	Menu getDefaultChild(@Param("supmenucode") int supmenucode,
			@Param("role_id") int role_id);
	
	/**
	 * get menu list by role id and level
	 * @param role_id
	 * @param level
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT B.menu_id,B.menu_name,B.menulevcod,B.supmenucode,B.menuurl,B.menuico FROM system_role_menu A,system_menu B WHERE A.menu_id=B.menu_id AND A.role_id=#{role_id} AND B.menulevcod=#{level} ORDER BY B.menulevcod,B.sort")
	List<Menu> getMenuListByRole(@Param("role_id") int role_id,
			@Param("level") String level);
	
	/**
	 * get leaf menu list by role id
	 * @param role_id
	 * @param supmenucode
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT B.menu_id,B.menu_name,B.menulevcod,B.supmenucode,B.menuurl,B.menuico FROM system_role_menu A,system_menu B WHERE A.menu_id=B.menu_id AND A.role_id=#{role_id} AND B.supmenucode=#{supmenucode} ORDER BY B.menulevcod,B.sort")
	List<Menu> getLeafMenuListByRole(@Param("role_id") int role_id,
			@Param("supmenucode") int supmenucode);
	
	/**
	 * get all menu list by role id
	 * @param role_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT B.menu_id,B.menu_name,B.menulevcod,B.supmenucode,B.menuurl,B.menuico FROM system_role_menu A,system_menu B WHERE A.menu_id=B.menu_id AND A.role_id=#{role_id} ORDER BY B.menulevcod,B.sort")
	List<Menu> getAllMenuListByRole(int role_id);
	
	/**
	 * get menu by role and id
	 * @param role_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT B.menu_id,B.menu_name,B.menulevcod,B.supmenucode,B.menuurl,B.menuico FROM system_role_menu A,system_menu B WHERE A.menu_id=B.menu_id AND A.role_id=#{role_id} AND B.menu_id=#{menu_id} ORDER BY B.menulevcod,B.sort")
	Menu getMenuByRoleAndId(@Param("role_id") int role_id,
			@Param("menu_id") int menu_id);
	
	/**
	 * get menu by role and URL
	 * @param role_id
	 * @param menuurl
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	@Select("SELECT B.menu_id,B.menu_name,B.menulevcod,B.supmenucode,B.menuurl,B.menuico FROM system_role_menu A,system_menu B WHERE A.menu_id=B.menu_id AND A.role_id=#{role_id} AND B.menuurl=#{menuurl} ORDER BY B.menulevcod,B.sort")
	Menu getMenuByRoleAndUrl(@Param("role_id") int role_id,
			@Param("menuurl") String menuurl);
}
