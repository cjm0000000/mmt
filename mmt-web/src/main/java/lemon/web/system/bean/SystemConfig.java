package lemon.web.system.bean;

/**
 * System configure
 * @author lemon
 * @version 1.0
 *
 */
public class SystemConfig {
	private String key;
	private String value;
	private String timestamp;
	
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
