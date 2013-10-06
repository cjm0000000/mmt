package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * role repository
 * @author lemon
 * @date Mar 20, 2012 1:19:56 PM
 * @version 1.0
 */
@Repository
public interface RoleMapper {
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
	 */
	void addRole(Role role);
	
	
	/**
	 * update role
	 * @param role
	 */
	void update(Role role);
	
	/**
	 * batch delete roles
	 * @param roles
	 */
	void batchDelete(String[] role_ids);
	
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
	 */
	void deleteRoleAuthority(int role_id);
	
	/**
	 * set role's authority
	 * @param role_id
	 * @param menu_ids
	 */
	void setRoleAuthority(@Param("role_id") int role_id,
			@Param("list") String[] menu_ids);
	
	/**
	 * set role's default authority
	 * @param role_id
	 */
	void setDefaultRoleAuthority(int role_id);
	
	/**
	 * get authority list by role_id
	 * @param role_id
	 * @return
	 */
	List<Menu> getAuthority(int role_id);
	
	/**
	 * set role's menu reload flag
	 * @param menu_id
	 */
	@Lang(RawLanguageDriver.class)
	void setReloadFlag(int menu_id);
	
	/**
	 * get role list by authority
	 * @param authority
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Role> getRoleListByAuthority(int authority);
	
}
