package lemon.shared.toolkit.weather.bean;

/**
 * City for weather
 * @author lemon
 * @version 1.0
 *
 */
public class City {
	private String citycode;
	private String city_name;
	private String province;
	
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
}
