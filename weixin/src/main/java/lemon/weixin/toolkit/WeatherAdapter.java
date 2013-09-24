package lemon.weixin.toolkit;

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

import lemon.shared.entity.WeatherInfo;
import lemon.shared.toolkit.Weather;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.message.Article;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.biz.WeiXinException;

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
		
		msg.setArticleCount(REPORT_DAYS);
		
		String[] days = getDaysArray(today, REPORT_DAYS);
		
		//加载模板
		initVelocity();
		VelocityContext context = new VelocityContext();
		context.put("wi", wi);
		context.put("days", days);
		context.put("msg", msg);
		StringWriter writer = new StringWriter();

		Velocity.mergeTemplate("lemon/weixin/toolkit/weather.xml", WeiXin.LOCAL_CHARSET, context, writer);
		System.out.println(writer);
		//FIXME 填充模板URL等信息
		//FIXME 对于实现AutoClose 接口的类，修改代码
		
		Article art1 = new Article();
		art1.setTitle("天气——" + wi.getCity());
		art1.setUrl("");
		art1.setPicUrl("");
		//TODO 详情需要弄个模板
		art1.setDescription(days[0]+"    " + wi.getWeek()+"  "+wi.getTemp1() +"  "+wi.getWeather1());
		
		Article art2 = new Article();
		art2.setTitle(days[1]);
		art2.setUrl("");
		art2.setPicUrl("");
		//TODO 详情需要弄个模板
		art2.setDescription(days[1]+"    " +wi.getTemp2() +"  "+wi.getWeather2());
		
		Article art3 = new Article();
		art3.setTitle(days[2]);
		art3.setUrl("");
		art3.setPicUrl("");
		//TODO 详情需要弄个模板
		art3.setDescription(days[2]+"    " + wi.getTemp3() +"  "+wi.getWeather3());
		
		Article art4 = new Article();
		art4.setTitle(days[3]);
		art4.setUrl("");
		art4.setPicUrl("");
		//TODO 详情需要弄个模板
		art4.setDescription(days[3]+"    " + wi.getTemp4() +"  "+wi.getWeather4());
		
		Article art5 = new Article();
		art5.setTitle(days[4]);
		art5.setUrl("");
		art5.setPicUrl("");
		//TODO 详情需要弄个模板
		art5.setDescription(days[4]+"    " + wi.getTemp5() +"  "+wi.getWeather5());
		
		Article[] articles = { art1, art2, art3, art4, art5 };
		
		//生成回复消息
		msg.setArticles(articles);
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
