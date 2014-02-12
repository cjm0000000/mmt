package com.github.cjm0000000.mmt.web.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.web.system.bean.SystemConfig;

/**
 * system configure repository
 * 
 * @author lemon
 * @version 1.0
 */
@Repository
public interface SysConfigRepository {
	
	/**
	 * add system configure item
	 * @param item
	 * @return
	 */
	@Insert("INSERT INTO system_config(`group`,`key`,value) VALUES(#{group},#{key},#{value})")
	@Lang(RawLanguageDriver.class)
	int addItem(SystemConfig item);
	
	/**
	 * delete system configure item
	 * @param group
	 * @param key
	 * @return
	 */
	@Delete("DELETE FROM system_config WHERE `group`=#{group} AND `key`=#{key}")
	@Lang(RawLanguageDriver.class)
	int deleteItem(@Param("group") String group, @Param("key") String key);
	
	/**
	 * delete system configure item
	 * @param group
	 * @return
	 */
	@Delete("DELETE FROM system_config WHERE `group`=#{group}")
	@Lang(RawLanguageDriver.class)
	int deleteItems(String group);
	
	/**
	 * get system configure item
	 * @param group
	 * @param key
	 * @return
	 */
	@Select("SELECT A.`group`, A.`key`, A.value, A.timestamp FROM system_config A WHERE A.`group`=#{group} AND A.`key`=#{key}")
	@Lang(RawLanguageDriver.class)
	SystemConfig getItem(@Param("group") String group, @Param("key") String key);
	
	/**
	 * get system configure item
	 * @param group
	 * @return
	 */
	@Select("SELECT A.`group`, A.`key`, A.value, A.timestamp FROM system_config A WHERE A.`group`=#{group}")
	@Lang(RawLanguageDriver.class)
	List<SystemConfig> getItems(String group);
	
	/**
	 * Update system configure item
	 * @param config
	 * @return
	 */
	@Update("UPDATE system_config A SET A.value=#{value} WHERE A.`group`=#{group} AND A.`key`=#{key}")
	int updateItem(SystemConfig config);
}
