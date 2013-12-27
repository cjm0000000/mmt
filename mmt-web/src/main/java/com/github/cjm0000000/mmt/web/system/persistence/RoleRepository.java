package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.Menu;
import com.github.cjm0000000.mmt.web.system.bean.Role;

/**
 * role repository
 * @author lemon
 * @date Mar 20, 2012 1:19:56 PM
 * @version 1.0
 */
@Repository
public interface RoleRepository {
	/**
	 * get role list
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Role> getRoleList(@Param("start")int start, @Param("limit")int limit);
	
	/**
	 * get role count
	 * @return
	 */
	int getRsCnt();
	
	/**
	 * add role
	 * @param role
	 * @return
	 */
	int addRole(Role role);
	
	
	/**
	 * update role
	 * @param role
	 * @return
	 */
	int update(Role role);
	
	/**
	 * batch delete roles
	 * @param role_ids
	 * @return
	 */
	int batchDelete(String[] role_ids);
	
	/**
	 * get role by id
	 * @param role_id
	 * @return
	 */
	Role getRole(int role_id);
	
	/**
	 * check if exists role name
	 * @param role_name
	 * @return
	 */
	int checkRoleName(String role_name);
	
	/**
	 * delete role's authority
	 * @param role_id
	 * @return
	 */
	int deleteRoleAuthority(int role_id);
	
	/**
	 * set role's authority
	 * @param role_id
	 * @param menu_ids
	 * @return
	 */
	int setRoleAuthority(@Param("role_id") int role_id,
			@Param("list") String[] menu_ids);
	
	/**
	 * set role's default authority
	 * @param role_id
	 * @return
	 */
	int setDefaultRoleAuthority(int role_id);
	
	/**
	 * get authority list by role_id
	 * @param role_id
	 * @return
	 */
	List<Menu> getAuthority(int role_id);
	
	/**
	 * set role's menu reload flag
	 * @param menu_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int setReloadFlag(int menu_id);
	
	/**
	 * get role list by authority
	 * @param authority
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Role> getRoleListByAuthority(int authority);
	
}
