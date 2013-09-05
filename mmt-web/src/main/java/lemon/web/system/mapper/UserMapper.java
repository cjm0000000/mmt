package lemon.web.system.mapper;

import java.util.List;
import java.util.Map;

import lemon.web.system.bean.User;
import lemon.web.system.bean.UserRole;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * User repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface UserMapper {
	
	/**
	 * get user id by user name
	 * @param user_name
	 * @return user_id
	 */
	@Select("SELECT A.user_id FROM system_user A WHERE A.user_name=#{user_name}")
	int getUserIdByName(String user_name);
	
	/**
	 * get user by id
	 * 
	 * @param id
	 * @return {@link lemon.web.system.bean.User User}
	 */
	@Select("SELECT A.user_id,A.user_name,A.`password`,A.xm,A.sfzh,A.sjhm,A.islock,A.bz,A.`status`,C.role_id,C.role_name FROM system_user A,system_user_role B ,system_role C WHERE A.user_id=B.user_id AND B.role_id=C.role_id AND A.user_id=#{user_id} AND A.`status`='AVAILABLE'")
	User getUserById(int user);

	/**
	 * verify user name and password
	 * @param user_name
	 * @param password
	 * @return {@link lemon.web.system.bean.User User}
	 */
	@Select("SELECT A.user_id,A.`password`,A.user_name,A.sfzh,A.sjhm,A.islock,A.bz,A.`status`,A.xm FROM system_user A WHERE A.user_name=#{user_name} AND A.`password`=#{password} AND A.`status`='AVAILABLE' AND A.islock='UNAVAILABLE'")
	User checkLogin(@Param("user_name") String user_name,
			@Param("password") String password);

	/**
	 * add user
	 * @param user
	 */
	@Insert("INSERT INTO system_user(password,user_name,sfzh,sjhm,bz,xm,islock,status) SELECT #{password},#{user_name},#{sfzh},#{sjhm},#{bz},#{xm},'UNAVAILABLE','AVAILABLE'")
	@Options(useGeneratedKeys=true,keyColumn="user_id",keyProperty="user_id")
	void addUser(User user);

	

	/**
	 * batch update user's status
	 * @param userIds
	 */
	@Update("")
	//FIXME dynamic SQL
	void updateUserStatus(String[] userIds);

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
