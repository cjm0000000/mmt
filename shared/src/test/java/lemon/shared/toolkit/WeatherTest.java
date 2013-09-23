package lemon.shared.toolkit;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import lemon.shared.entity.WeatherInfo;
import lemon.shared.mapper.CityMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WeatherTest {
	private AbstractApplicationContext acx;
	private CityMapper cityMapper;
	private Weather weather;
	
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		cityMapper = acx.getBean(CityMapper.class);
		weather = acx.getBean(Weather.class);
		assertNotNull(cityMapper);
		assertNotNull(weather);
	}
	
	@After
	public void destory(){
		acx.close();
	}
	
	@Test
	@Ignore
	public void initCity() throws IOException{
		weather.initCity();
	}
	
	@Test
	public void searchWeather(){
		String city = "杭州";
		WeatherInfo result = weather.searchWeather(city);
		System.out.println(result);
	}
}
