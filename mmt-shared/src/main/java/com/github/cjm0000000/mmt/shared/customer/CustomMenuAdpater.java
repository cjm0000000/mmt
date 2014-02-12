package com.github.cjm0000000.mmt.shared.customer;

import java.util.List;

/**
 * 自定义菜单适配器
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenuAdpater {
	private String name;
	private String type;
	private String url;
	private String key;
	private List<CustomMenuAdpater> sub_button;
	
	public CustomMenuAdpater(){}
	
	public CustomMenuAdpater(String name, String type, String url,
			String key, List<CustomMenuAdpater> sub_button) {
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
	public List<CustomMenuAdpater> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<CustomMenuAdpater> sub_button) {
		this.sub_button = sub_button;
	}
	
}
