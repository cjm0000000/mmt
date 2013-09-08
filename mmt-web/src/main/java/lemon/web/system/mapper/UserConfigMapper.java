package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.UserConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * user configure repository
 * 
 * @author lemon
 * @date May 14, 2012 10:18:34 AM
 * @version 1.0
 */
@Repository
public interface UserConfigMapper {
	
	/**
	 * get user configure item
	 * @param user_id
	 * @param key
	 * @return
	 */
	@Select("SELECT A.user_id, A.`key`, A.value FROM system_user_config A WHERE A.user_id=#{user_id} AND A.key=#{key}")
	UserConfig getItem(@Param("user_id") int user_id, @Param("key") String key);

	/**
	 * get user configure items
	 * @param user_id
	 * @return
	 */
	@Select("SELECT A.user_id, A.`key`, A.value FROM system_user_config A WHERE A.user_id=#{user_id}")
	List<UserConfig> getItems(int user_id);

	/**
	 * save user configure item
	 * @param item
	 */
	@Insert("INSERT INTO system_user_config(user_id,key,value) SELECT #{user_id},#{key},#{value}")
	void saveItem(UserConfig item);

	/**
	 * delete user configure item
	 * @param user_id
	 * @param key
	 */
	@Delete("DELETE system_user_config A WHERE A.user_id=#{user_id} AND A.key=#{key}")
	void deleteItem(@Param("user_id") int user_id, @Param("key") String key);
}
