package lemon.weixin.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lemon.shared.entity.WeatherInfo;
import lemon.shared.toolkit.Weather;
import lemon.weixin.bean.message.Article;
import lemon.weixin.bean.message.NewsMessage;

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
	public void generateWeatherReport(String city, NewsMessage msg){
		WeatherInfo wi = weather.searchWeather(city);
		if(null == wi)
			return;
		Date today = null;
		try {
			today = sdf.parse(wi.getDate_y());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(null == today)
			return;
		
		String[] days = getDaysArray(today, REPORT_DAYS);
		
		Article art1 = new Article();
		art1.setTitle("天气——" + wi.getCity());
		art1.setUrl("");
		art1.setPicUrl("");
		//TODO 详情需要弄个模板
		art1.setDescription(days[0]);
		
		Article art2 = new Article();
		art2.setTitle("天气——" + wi.getCity());
		art2.setUrl("");
		art2.setPicUrl("");
		//TODO 详情需要弄个模板
		art2.setDescription(days[1]);
		
		Article art3 = new Article();
		art3.setTitle("天气——" + wi.getCity());
		art3.setUrl("");
		art3.setPicUrl("");
		//TODO 详情需要弄个模板
		art3.setDescription(days[2]);
		
		Article art4 = new Article();
		art4.setTitle("天气——" + wi.getCity());
		art4.setUrl("");
		art4.setPicUrl("");
		//TODO 详情需要弄个模板
		art4.setDescription(days[3]);
		
		Article art5 = new Article();
		art5.setTitle("天气——" + wi.getCity());
		art5.setUrl("");
		art5.setPicUrl("");
		//TODO 详情需要弄个模板
		art5.setDescription(days[4]);
		
		Article[] articles = { art1, art2, art3, art4, art5 };
		
		//生成回复消息
		msg.setArticles(articles);
		msg.setArticleCount(articles.length);
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
	
}
