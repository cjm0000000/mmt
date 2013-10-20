package lemon.shared.toolkit.json;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * JSON转换工具类
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public final class JSONHelper {
	/**
	 * 过滤值为空的属性
	 * 
	 * @return
	 */
	public static final JsonConfig filterNull() {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(filterPropertyNull());
		return config;
	}

	/**
	 * 过滤值为空的属性
	 * 
	 * @return
	 */
	private static PropertyFilter filterPropertyNull() {
		return new PropertyFilter() {
			@Override
			public boolean apply(Object source, String key, Object value) {
				return value == null;
			}
		};
	}

}
