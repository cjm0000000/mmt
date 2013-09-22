package lemon.shared.mapper;

import lemon.shared.entity.City;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * City Repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface CityMapper {

	/**
	 * 添加城市
	 * @param city
	 */
	@Insert("INSERT INTO mmt_city(citycode,city_name,province) VALUES(#{citycode},#{city_name},#{province})")
	@Lang(RawLanguageDriver.class)
	void add(City city);
	
	/**
	 * 根据名称查询城市
	 * @param city_name
	 * @return
	 */
	@Select("SELECT A.citycode,A.city_name,A.province FROM mmt_city A WHERE A.city_name=#{city_name}")
	@Lang(RawLanguageDriver.class)
	City get(String city_name);
	
	/**
	 * 清空数据
	 */
	//TODO 最好改成truncate
	@Delete("DELETE FROM mmt_city")
	void clean();
}
