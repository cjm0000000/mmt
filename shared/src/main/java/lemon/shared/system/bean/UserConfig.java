package lemon.shared.system.bean;

import java.util.Date;

public class UserConfig {
	private int user_id;
	private String key;
	private String value;
	private Date create_time;
	private Date lastmodify_time;
	
	public UserConfig(){}
	
	public UserConfig(int user_id, String key, String value){
		this.user_id = user_id;
		this.key = key;
		this.value = value;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreate_time() {
		return (Date) create_time.clone();
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getLastmodify_time() {
		return (Date) lastmodify_time.clone();
	}
	public void setLastmodify_time(Date lastmodify_time) {
		this.lastmodify_time = lastmodify_time;
	}
	
}
