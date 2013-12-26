package com.github.cjm0000000.mmt.weixin.weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.message.send.passive.NewsMessage;
import com.github.cjm0000000.mmt.shared.weather.Weather;
import com.github.cjm0000000.mmt.shared.weather.WeatherInfo;
import com.github.cjm0000000.mmt.weixin.WeiXinException;

@Service
public class WeatherAdapter {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	/** 天气预报的天数 */
	private static final int REPORT_DAYS = 5;
	@Autowired
	private Weather weather;

	/**
	 * 把Weather信息生成图文消息
	 * @param city
	 * @param msg
	 */
	public NewsMessage generateWeatherReport(String city, NewsMessage msg){
		WeatherInfo wi = weather.searchWeather(city);
		if(null == wi)
			return null;
		Date today = null;
		try {
			today = sdf.parse(wi.getDate_y());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(null == today)
			return null;
		
		String[] days = getDaysArray(today, REPORT_DAYS);
		
		//加载模板
		initVelocity();
		VelocityContext context = new VelocityContext();
		context.put("wi", wi);
		context.put("days", days);
		context.put("msg", msg);
		StringWriter writer = new StringWriter();

		Velocity.mergeTemplate("lemon/weixin/toolkit/weather.xml", MmtCharset.LOCAL_CHARSET, context, writer);
		//TODO 天气消息直接转NewsMessage
		return null;
	}
	
	/**
	 * 获取未来几天日期
	 * @param today
	 * @param limit 需要获取的天数
	 * @return
	 */
	private String[] getDaysArray(Date today, int limit){
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		List<String> dateList = new ArrayList<>();
		for (int i = 0; i < limit; i++) {
			dateList.add(sdf.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dateList.toArray(new String[5]);
	}
	
	/**
	 * 初始化Velocity引擎
	 */
	private void initVelocity() {
		Properties p = new Properties();
		try (InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("velocity.properties")) {
			p.load(in);
		} catch (IOException e) {
			throw new WeiXinException("Velocity initlize faild.", e.getCause());
		}
		Velocity.init(p);
	}
	
}
