package com.github.cjm0000000.shared.test.weather;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.shared.weather.City;
import com.github.cjm0000000.mmt.shared.weather.Weather;
import com.github.cjm0000000.mmt.shared.weather.WeatherInfo;
import com.github.cjm0000000.mmt.shared.weather.persistence.CityRepository;
import com.github.cjm0000000.shared.test.AbstractTester;

/**
 * Unit test case for weather
 * @author lemon
 * @version 1.0
 *
 */
public class Weather_Test extends AbstractTester {
	@Autowired
	private CityRepository cityMapper;
	@Autowired
	private Weather weather;
	
	@Override
	public void defaultCase() {
		searchWeather();
	}
	
	@Test
	@Ignore
	public void initCity() throws IOException{
		weather.initCity();
	}
	
	@Test
	@Ignore
	public void addCity(){
		City c = new City();
		c.setCity_name("AAS");
		c.setCitycode("asdq");
		c.setProvince("B");
		cityMapper.add(c);
	}
	
	//FIXME 获取城市名称   http://maps.google.com/maps/api/geocode/json?latlng=23.149207,113.256196&language=zh-CN&sensor=true
	private void searchWeather(){
		String city = "杭州";
		WeatherInfo result = weather.searchWeather(city);
		assertNotNull(result);
	}

}
