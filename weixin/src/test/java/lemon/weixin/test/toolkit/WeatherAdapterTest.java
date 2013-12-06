package lemon.weixin.test.toolkit;

import static org.junit.Assert.assertNotNull;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.weixin.test.base.BaseWeiXinTest;
import lemon.weixin.toolkit.WeatherAdapter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherAdapterTest extends BaseWeiXinTest {
	@Autowired
	private WeatherAdapter weatherAdapter;
	
	@Test
	public void generateWeatherReport(){
		String cityName = "杭州";
		NewsMessage msg = new NewsMessage();
		msg.setFromUserName("fromUserName");
		assertNotNull(weatherAdapter.generateWeatherReport(cityName, msg));
	}
	
}
