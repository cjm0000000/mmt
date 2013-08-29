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
	
	/**
	 * Send message to WeiXin and receive a message from WeiXin<BR>
	 * default timeout is 5 seconds.
	 * @param token	
	 * 			customer's token
	 * @param url	request URL
	 * @param method	request method, GET or POST
	 * @param params	request params
	 * @return
	 */
	public static String send(String token, String url, String method, Object... params) {
		// TODO Implement message sender
		return null;
	}
}
