package lemon.weixin.toolkit;

import static org.junit.Assert.assertNotNull;
import lemon.weixin.message.bean.NewsMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WeatherAdapterTest {
	private WeatherAdapter weatherAdapter;
	private AbstractApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		weatherAdapter = acx.getBean(WeatherAdapter.class);
		assertNotNull(weatherAdapter);
	}
	@After
	public void destory(){
		acx.close();
	}
	
	@Test
	public void generateWeatherReport(){
		String cityName = "杭州";
		NewsMessage msg = new NewsMessage();
		msg.setFromUserName("fromUserName");
		weatherAdapter.generateWeatherReport(cityName, msg);
	}
	
}
