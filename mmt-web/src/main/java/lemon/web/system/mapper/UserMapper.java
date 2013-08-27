package lemon.web.system.mapper;

import java.util.List;
import java.util.Map;

import lemon.web.system.bean.User;
import lemon.web.system.bean.UserRole;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	
	int getUserId(String user_name);
	
	/**
	 * 获取用户角色列表
	 * 
	 * @param userId
	 * @return
	 */
	List<UserRole> getRolesByUserId(int userId);
	
	/**
	 * 登录获取用户角色列表
	 * @param userId
	 * @return
	 */
	List<User> getRolesByLogin(int userId);

	/**
	 * 得到一个用户
	 * 
	 * @param id
	 * @return
	 */
	User getUserByIdAndRole(User user);

	/**
	 * 判断用户登录
	 * 
	 * @param map
	 * @return
	 */
	User checkLogin(User user);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 用户资料变更
	 * 
	 * @param user
	 */
	void yhzlbg(User user);
	
	/**
	 * 首页个人设置
	 * 
	 * @param user
	 */
	void grsz(User user);

	/**
	 * 删除用户(伪删除)
	 * 
	 * @param users
	 */
	void deleteUser(String[] users);

	/**
	 * 获取用户列表 String dept_id, String user_name, Integer status, int page, int pageSize
	 * 
	 * @return
	 */
	List<User> getUserList(List<Integer> users);
	
	/**
	 * 获取用户ID列表
	 * @param map
	 * @return
	 */
	List<Integer> getUserIdList(Map<String,Object> map);

	/**
	 * 获取用户数量
	 * String dept_id, String user_name, Integer status
	 * 
	 * @return
	 */
	int getUserCnt(Map<String, Object> map);
	
	/**
	 * 用户登陆时更新信息
	 * @param user
	 */
	void updateUserByLogin(User user);
	/**
	 * 添加用户角色
	 * @param user
	 */
	void addUserRole(UserRole userRole);
	/**
	 * 更新用户角色
	 * @param userRole
	 */
	void editUserRole(UserRole userRole);
	/**
	 * 根据ID删除用户角色
	 * @param userRoleId
	 */
	void delUserRole(int userRoleId);
	/**
	 * 清空用户角色
	 * @param user_id
	 */
	void clearUserRole(int user_id);
	/**
	 * 验证用户名是否存在
	 * @param user_name
	 * @return
	 */
	int checkUserName(String user_name);
	/**
	 * 根据ID获取用户
	 * @param user_id
	 * @return
	 */
	User getUser(int user_id);
	
	/**
	 * 获取用户
	 * @param map
	 * @return
	 */
	List<User> userGrid(Map<String, Object> map);
	
	/**
	 * 根据部门ID和角色ID获取用户信息
	 * @param user
	 * @return
	 */
	List<User> getUsers(User user);
}
