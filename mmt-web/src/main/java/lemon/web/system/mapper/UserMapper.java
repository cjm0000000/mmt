package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	void updateUserStatus(String[] userIds);

	/**
	 * get user list
	 * 
	 * @return
	 */
	@Select("SELECT A.user_id,A.user_name,A.`password`,A.xm,A.sfzh,A.sjhm,A.islock,A.bz,A.`status`,C.role_id,C.role_name FROM system_user A,system_user_role B ,system_role C WHERE A.user_id=B.user_id AND B.role_id=C.role_id AND A.`status`='AVAILABLE' limit #{start},#{limit}")
	List<User> getUserList(@Param("start")int page, @Param("limit")int limit);
	
	/**
	 * get user count
	 * @return
	 */
	@Select("SELECT COUNT(A.user_id) FROM system_user A WHERE A.`status`='AVAILABLE'")
	int getUserCnt();
	
	/**
	 * add user's role
	 * @param user_id
	 * @param role_id
	 */
	@Insert("INSERT INTO system_user_role(user_id,role_id) SELECT #{user_id},#{role_id}")
	void addUserRole(@Param("user_id") int user_id,
			@Param("role_id") int role_id);
	
	/**
	 * delete user's role
	 * @param user_id
	 */
	@Delete("DELETE FROM system_user_role WHERE user_id=#{user_id}")
	void delUserRole(int user_id);
}
