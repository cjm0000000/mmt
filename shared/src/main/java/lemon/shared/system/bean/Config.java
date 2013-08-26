package lemon.shared.system.bean;
/**
 * 系统配置
 * @author 张连明
 * @date May 14, 2012 10:08:20 AM
 */
public class Config {
	private String config_name,key,value,config_sm;
	public Config(){}
	public Config(String config_name, String key, String value, String config_sm) {
		super();
		this.config_name = config_name;
		this.key = key;
		this.value = value;
		this.config_sm = config_sm;
	}

	public String getConfig_name() {
		return config_name;
	}

	public void setConfig_name(String config_name) {
		this.config_name = config_name;
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

	public String getConfig_sm() {
		return config_sm;
	}

	public void setConfig_sm(String config_sm) {
		this.config_sm = config_sm;
	}
}
