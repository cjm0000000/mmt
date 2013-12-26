package com.github.cjm0000000.mmt.weixin.test.toolkit;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;
import com.github.cjm0000000.mmt.weixin.test.AbstractWeiXinTester;
import com.github.cjm0000000.mmt.weixin.weather.WeatherAdapter;

public class WeatherAdapter_Test extends AbstractWeiXinTester {
	@Autowired
	private WeatherAdapter weatherAdapter;
	
	@Override
	protected void defaultCase() {
		String cityName = "杭州";
		NewsMessage msg = new NewsMessage();
		msg.setFromUserName("fromUserName");
		assertNotNull(weatherAdapter.generateWeatherReport(cityName, msg));
	}
	
}
