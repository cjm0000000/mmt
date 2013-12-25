package lemon.shared.test.toolkit;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import lemon.shared.test.base.BaseMmtTest;
import lemon.shared.toolkit.weather.Weather;
import lemon.shared.toolkit.weather.bean.City;
import lemon.shared.toolkit.weather.bean.WeatherInfo;
import lemon.shared.toolkit.weather.mapper.CityMapper;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherTest extends BaseMmtTest {
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private Weather weather;
	
	@Test
	@Ignore
	public void initCity() throws IOException{
		weather.initCity();
	}
	
	//FIXME 获取城市名称   http://maps.google.com/maps/api/geocode/json?latlng=23.149207,113.256196&language=zh-CN&sensor=true
	
	@Test
	public void searchWeather(){
		String city = "杭州";
		WeatherInfo result = weather.searchWeather(city);
		assertNotNull(result);
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
}
