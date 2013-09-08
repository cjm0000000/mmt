package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.User;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
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
	 * add user
	 * @param user
	 */
	@Lang(RawLanguageDriver.class)
	void addUser(User user);
	
	/**
	 * add user's role
	 * @param user_id
	 * @param role_id
	 * @param cust_id
	 */
	@Lang(RawLanguageDriver.class)
	void addUserRole(@Param("user_id") int user_id,
			@Param("role_id") int role_id, @Param("cust_id") int cust_id);
	
	/**
	 * get user id by user name
	 * @param user_name
	 * @return user_id
	 */
	@Lang(RawLanguageDriver.class)
	int getUserIdByName(String user_name);
	
	/**
	 * get user by id
	 * 
	 * @param id
	 * @return {@link lemon.web.system.bean.User User}
	 */
	@Lang(RawLanguageDriver.class)
	User getUserById(int user_id);

	/**
	 * verify user name and password
	 * @param user_name
	 * @param password
	 * @return {@link lemon.web.system.bean.User User}
	 */
	@Lang(RawLanguageDriver.class)
	User checkLogin(@Param("user_name") String user_name,
			@Param("password") String password);

	/**
	 * batch update user's status to 'UNAVAILABLE'
	 * @param userIds
	 */
	void deleteUser(int[] userIds);

	/**
	 * get user list
	 * @param page
	 * @param limit
	 * @param user_name
	 * @return
	 */
	List<User> getUserList(@Param("start") int page, @Param("limit") int limit,
			@Param("user_name") String user_name);
	
	/**
	 * get user count
	 * @param user_name
	 * @return
	 */
	int getUserCnt(@Param("user_name") String user_name);
	
	/**
	 * update user role and customer
	 * @param user_id
	 * @param role_id
	 * @param cust_id
	 */
	@Lang(RawLanguageDriver.class)
	void updateUserRole(@Param("user_id") int user_id,
			@Param("role_id") int role_id, @Param("cust_id") int cust_id);
}
