package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.User;

/**
 * User repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface UserRepository {
	
	/**
	 * add user
	 * @param user
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addUser(User user);
	
	/**
	 * add user's role
	 * @param user_id
	 * @param role_id
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addUserRole(@Param("user_id") int user_id,
			@Param("role_id") int role_id, @Param("cust_id") int cust_id);
	
	/**
	 * Get user id by user name.<BR>
	 * If user not exists, return null
	 * @param username
	 * @return user_id
	 */
	@Lang(RawLanguageDriver.class)
	Integer getUserIdByName(String username);
	
	/**
	 * get user by id
	 * 
	 * @param id
	 * @return {@link com.github.cjm0000000.mmt.web.system.bean.User User}
	 */
	@Lang(RawLanguageDriver.class)
	User getUserById(int user_id);

	/**
	 * verify user name and password
	 * @param username
	 * @param password
	 * @return {@link com.github.cjm0000000.mmt.web.system.bean.User User}
	 */
	@Lang(RawLanguageDriver.class)
	User checkLogin(@Param("username") String user_name,
			@Param("password") String password);

	/**
	 * batch update user's status to 'UNAVAILABLE'
	 * @param userIds
	 * @return
	 */
	int deleteUser(String[] userIds);

	/**
	 * get user list
	 * @param page
	 * @param limit
	 * @param user_name
	 * @return
	 */
	List<User> getUserList(@Param("start") int page, @Param("limit") int limit,
			@Param("username") String user_name);
	
	/**
	 * get user count
	 * @param user_name
	 * @return
	 */
	int getUserCnt(@Param("username") String user_name);
	
	/**
	 * update user role and customer
	 * @param user_id
	 * @param role_id
	 * @param cust_id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int updateUserRole(@Param("user_id") int user_id,
			@Param("role_id") int role_id, @Param("cust_id") int cust_id);
	
	/**
	 * update user
	 * @param user
	 * @return
	 */
	int updateUser(User user);
}
