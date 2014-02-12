package com.github.cjm0000000.mmt.shared.weather.persistence;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.shared.weather.City;

/**
 * City Repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface CityRepository {

	/**
	 * 添加城市
	 * @param city
	 */
	@Insert("INSERT INTO city(citycode,city_name,province,city_alias) VALUES(#{citycode},#{city_name},#{province},CONCAT(#{province},#{city_name}))")
	@Lang(RawLanguageDriver.class)
	int add(City city);
	
	/**
	 * 根据名称查询城市
	 * @param city_name
	 * @return
	 */
	@Select("SELECT A.citycode,A.city_name,A.province FROM city A WHERE (A.city_name=#{city_name} OR A.city_alias=#{city_name})")
	@Lang(RawLanguageDriver.class)
	City get(String city_name);
	
	/**
	 * 清空数据
	 */
	//TODO 最好改成truncate
	@Delete("DELETE FROM city")
	int clean();
}
