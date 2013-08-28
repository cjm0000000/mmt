package lemon.weixin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lemon.weixin.bean.WeiXinConfig;

/**
 * WeiXin configures
 * 
 * @author lemon
 * 
 */
public class WeiXin {
	
	private static ConcurrentMap<String, WeiXinConfig> configs;

	public static void init() {
		if (configs == null)
			configs = new ConcurrentHashMap<>();
		else
			configs.clear();
	}

	/**
	 * 获取WeiXin通用配置
	 * 
	 * @param token
	 * @return
	 */
	public static WeiXinConfig getConfig(String token) {
		return configs.get(token);
	}

	/**
	 * 更改WeiXin通用配置
	 * 
	 * @param token
	 * @param config
	 */
	public static void setConfig(String token, WeiXinConfig config) {
		configs.put(token, config);
	}
	
	public static void destory(){
		if(null != configs)
			configs.clear();
	}
}
