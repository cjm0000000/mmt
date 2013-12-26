package com.github.cjm0000000.mmt.shared.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cjm0000000.mmt.shared.toolkit.http.HttpConnector;
import com.github.cjm0000000.mmt.shared.weather.persistence.CityRepository;

/**
 * 天气查询API<br>
 * 采用中央气象台的接口
 * @author lemon
 * @version 1.0
 *
 */
@Service
public class Weather {
	/** 查询省份信息URL */
	private static final String SEARCH_PROVINCE_URL = "http://m.weather.com.cn/data5/city.xml";
	/** 查询城市信息URL */
	private static final String SEARCH_CITY_URL = "http://m.weather.com.cn/data5/city#provinceCode#.xml";
	/** 查询县区信息URL */
	private static final String SEARCH_TOWN_URL = "http://m.weather.com.cn/data5/city#cityCode#.xml";
	/** 获取CITYID信息URL */
	private static final String SEARCH_CITYID_URL = "http://m.weather.com.cn/data5/city#townCode#.xml";
	/** 获取城市天气信息URL */
	private static final String SEARCH_WEATHER_URL = "http://m.weather.com.cn/data/#cityid#.html";
	@Autowired
	private CityRepository cityMapper;
	
	/**
	 * 根据城市名称查询天气
	 * @param cityName
	 * @return
	 */
	public WeatherInfo searchWeather(String cityName){
		City city = cityMapper.get(analyzeCityName(cityName));
		if(city == null)
			return null;
		String weatherUrl = SEARCH_WEATHER_URL.replaceAll("#cityid#", city.getCitycode());
		JSONObject json = JSON.parseObject(HttpConnector.get(weatherUrl));
		return (WeatherInfo) JSON.toJavaObject(json.getJSONObject("weatherinfo"), WeatherInfo.class);
	}

	/**
	 * 初始化城市数据
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void initCity() throws IOException {
		String[] provinces = searchProvinceCode();
		if (null == provinces)
			return;
		//清空数据
		cityMapper.clean();
		//循环省份
		for (String province : provinces) {
			String[] provinceTemp = province.split("\\|");
			if (provinceTemp.length < 2)
				continue;
			//省份代码
			String provinceCode = provinceTemp[0];
			//省份名称
			String provinceName = provinceTemp[1];
			//查询城市代码
			String[] citys = searchCityCode(provinceCode);
			if (null == citys)
				continue;
			//循环城市
			for (String city : citys) {
				String[] cityTemp = city.split("\\|");
				if(cityTemp.length < 2)
					continue;
				//城市代码
				String cityCode = cityTemp[0];
				//查询县区代码
				String[] towns = searchTownCode(cityCode);
				if (null == towns)
					continue;
				//循环县区
				for (String town : towns) {
					String[] townTemp = town.split("\\|");
					if(townTemp.length < 2)
						continue;
					//县区代码
					String townCode = townTemp[0];
					//县区名称
					String townName = townTemp[1];
					String cityid = searchCityId(townCode);
					cityMapper.add(buildCity(townName, cityid, provinceName));
				}
				
			}
		}
	}

	/**
	 * 查询省份信息<br>
	 * 返回数组，数组里面的格式是：02|上海,03|天津
	 */
	private String[] searchProvinceCode() {
		String result = HttpConnector.get(SEARCH_PROVINCE_URL);
		if (result == null)
			return null;
		return result.split(",");
	}

	/**
	 * 查询城市信息<br>
	 * 返回数组，数组里面的格式是：2801|广州,2802|韶关
	 * @param provinceCode
	 * @return
	 */
	private String[] searchCityCode(String provinceCode) {
		String cityUrl = SEARCH_CITY_URL.replaceAll("#provinceCode#", provinceCode);
		String result = HttpConnector.get(cityUrl);
		return result.split(",");
	}
	
	/**
	 * 查询县区信息<br>
	 * 返回数组，数组里面的格式是：280101|广州,280102|番禺,280103|从化
	 * @param cityCode
	 * @return
	 */
	private String[] searchTownCode(String cityCode){
		String townUrl = SEARCH_TOWN_URL.replaceAll("#cityCode#", cityCode);
		String result = HttpConnector.get(townUrl);
		return result.split(",");
	}
	
	/**
	 * 查询CITYID
	 * @param townCode
	 * @return
	 */
	private String searchCityId(String townCode){
		String townUrl = SEARCH_CITYID_URL.replaceAll("#townCode#", townCode);
		String result = HttpConnector.get(townUrl);
		String[] temp =  result.split("\\|");
		if(temp.length == 2)
			return temp[1];
		return null;
	}
	
	/**
	 * 生产City对象
	 * @param city_name
	 * @param citycode
	 * @param province
	 * @return
	 */
	private City buildCity(String city_name, String citycode, String province) {
		City city = new City();
		city.setCity_name(city_name);
		city.setCitycode(citycode);
		city.setProvince(province);
		return city;
	}
	
	/**
	 * 分析城市名称
	 * @param cityName
	 * @return
	 */
	//TODO 添加更加智能的城市名称分析
	private String analyzeCityName(String cityName){
		cityName = cityName.replaceAll("省", "");
		cityName = cityName.replaceAll("市", "");
		cityName = cityName.replaceAll("自治区", "");
		if(cityName.startsWith("中国"))
			cityName = cityName.substring(2);
		return cityName;
	}
	
}
