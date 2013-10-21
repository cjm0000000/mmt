package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.SystemConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * system configure repository
 * 
 * @author lemon
 * @version 1.0
 */
@Repository
public interface SystemConfigMapper {
	
	/**
	 * add system configure item
	 * @param item
	 * @return
	 */
	@Insert("INSERT INTO system_config(`group`,`key`,value) SELECT #{group},#{key},#{value}")
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
	@Select("SELECT A.`group`, A.`key`, A.value, A.timestamp FROM system_config A WHERE A.`group`=#{group} AND A.key=#{key}")
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
}
