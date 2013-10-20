package lemon.weixin.custommenu.bean;

import java.util.ArrayList;
import java.util.List;

import lemon.shared.toolkit.json.JSONHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 微信自定义菜单适配器
 * @author lemon
 * @version 1.0
 *
 */
public class WXCustomMenuAdpater {
	private String name;
	private String type;
	private String url;
	private String key;
	private List<WXCustomMenuAdpater> sub_button;
	
	public WXCustomMenuAdpater(){}
	
	public WXCustomMenuAdpater(String name, String type, String url,
			String key, List<WXCustomMenuAdpater> sub_button) {
		super();
		this.name = name;
		this.type = type;
		this.url = url;
		this.key = key;
		this.sub_button = sub_button;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<WXCustomMenuAdpater> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<WXCustomMenuAdpater> sub_button) {
		this.sub_button = sub_button;
	}
	
	public static void main(String[] args){
		WXCustomMenuAdpater r1 = new WXCustomMenuAdpater();
		r1.setName("今日歌曲");
		r1.setKey("KEY000001");
		r1.setType("click");
		
		WXCustomMenuAdpater r2 = new WXCustomMenuAdpater();
		r2.setName("歌手介绍");
		r2.setUrl("www.baidu.com");
		r2.setType("view");
		
		WXCustomMenuAdpater r3 = new WXCustomMenuAdpater();
		r3.setName("其他");
		r3.setKey("KEY000003");
		r3.setType("click");
		
		WXCustomMenuAdpater r4 = new WXCustomMenuAdpater();
		r4.setName("其他1");
		r4.setKey("KEY0000031");
		r4.setType("click");
		
		WXCustomMenuAdpater r5 = new WXCustomMenuAdpater();
		r5.setName("其他2");
		r5.setKey("KEY0000032");
		r5.setType("click");
		List<WXCustomMenuAdpater> r3_sub = new ArrayList<WXCustomMenuAdpater>();
		r3_sub.add(r4);
		r3_sub.add(r5);
		r3.setSub_button(r3_sub);
		
		List<WXCustomMenuAdpater> ff = new ArrayList<WXCustomMenuAdpater>();
		ff.add(r1);
		ff.add(r2);
		ff.add(r3);
		
		JSONArray json = JSONArray.fromObject(ff,JSONHelper.filterNull());
		
		JSONObject result = new JSONObject();
		result.put("button", json);
		System.out.println(result.toString());
	}
	
}
