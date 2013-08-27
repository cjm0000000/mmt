package lemon.web.system.mapper;

import java.util.List;
import java.util.Map;

import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Role;

import org.springframework.stereotype.Repository;

/**
 * 获取角色信息DAO接口
 * @author 张连明
 * @date Mar 20, 2012 1:19:56 PM
 */
@Repository
public interface RoleMapper {
	/** 获取角色列表 */
	List<Role> getRoles(Map<String, Object> map);
	/** 获取角色数量 */
	int getRsCnt();
	/** 添加角色 */
	void addRole(Role role);
	/** 更新角色信息 */
	void update(Role role);
	/** 删除角色 */
	void delete(String[] roles);
	/** 删除角色权限 */
	void deleteRoleAuthority(int role_id);
	/** 设置权限 */
	void setRoleAuthority(Map<String, Object> map);
	/** 设置默认权限 */
	void setDefaultRoleAuthority(int role_id);
	/** 获取单个角色信息 */
	Role getRole(int role_id);
	/** 验证角色是否存在 */
	int checkRoleName(String role_name);
	/** 获取权限列表 */
	List<Menu> getAuthority(int role_id);
	/** 更新reload状态 */
	void updateReload(int role_id);
}
