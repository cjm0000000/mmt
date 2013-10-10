package lemon.web.system.mapper;

import lemon.web.system.bean.SystemConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
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
	@Insert("INSERT INTO system_config(`key`,value) SELECT #{key},#{value}")
	@Lang(RawLanguageDriver.class)
	int addItem(SystemConfig item);
	
	/**
	 * delete system configure item
	 * @param key
	 * @return
	 */
	@Delete("DELETE FROM system_config WHERE `key`=#{key}")
	@Lang(RawLanguageDriver.class)
	int deleteItem(String key);
	
	/**
	 * get system configure item
	 * @param key
	 * @return
	 */
	@Select("SELECT A.`key`, A.value, A.timestamp FROM system_config A WHERE A.key=#{key}")
	@Lang(RawLanguageDriver.class)
	SystemConfig getItem(String key);
}
