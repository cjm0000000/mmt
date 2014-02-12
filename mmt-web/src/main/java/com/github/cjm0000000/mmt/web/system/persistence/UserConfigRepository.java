package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.UserConfig;

/**
 * user configure repository
 * 
 * @author lemon
 * @date May 14, 2012 10:18:34 AM
 * @version 1.0
 */
@Repository
public interface UserConfigRepository {
	
	/**
	 * add user configure item
	 * @param item
	 * @return
	 */
	@Insert("INSERT INTO system_user_config(user_id,`key`,value) VALUES(#{user_id},#{key},#{value})")
	@Lang(RawLanguageDriver.class)
	int addItem(UserConfig item);
	
	/**
	 * delete user configure item
	 * @param user_id
	 * @param key
	 * @return
	 */
	@Delete("DELETE FROM system_user_config WHERE user_id=#{user_id} AND `key`=#{key}")
	@Lang(RawLanguageDriver.class)
	int deleteItem(@Param("user_id") int user_id, @Param("key") String key);
	
	/**
	 * delete user configure items
	 * @param user_id
	 * @return
	 */
	@Delete("DELETE FROM system_user_config WHERE user_id=#{user_id}")
	@Lang(RawLanguageDriver.class)
	int deleteItems(@Param("user_id") int user_id);
	
	/**
	 * get user configure item
	 * @param user_id
	 * @param key
	 * @return
	 */
	@Select("SELECT A.user_id, A.`key`, A.value FROM system_user_config A WHERE A.user_id=#{user_id} AND A.`key`=#{key}")
	@Lang(RawLanguageDriver.class)
	UserConfig getItem(@Param("user_id") int user_id, @Param("key") String key);

	/**
	 * get user configure items
	 * @param user_id
	 * @return
	 */
	@Select("SELECT A.user_id, A.`key`, A.value FROM system_user_config A WHERE A.user_id=#{user_id}")
	@Lang(RawLanguageDriver.class)
	List<UserConfig> getItems(int user_id);
}
