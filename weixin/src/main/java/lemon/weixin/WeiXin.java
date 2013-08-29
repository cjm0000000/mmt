package lemon.weixin;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.biz.WeiXinException;
import lemon.weixin.gateway.MicroChatMessager;

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
	 * POST a message to the URL, and receive a message<BR>
	 * default timeout is 5 seconds.
	 * @param url
	 * @param msg
	 * @param params
	 * @return
	 */
	public static String postMsg(String url, String msg, Object... params){
		String replyMsg = null;
		try {
			replyMsg = MicroChatMessager.send(url, "POST", msg, params);
		} catch (IOException e) {
			throw new WeiXinException("POST message faild: " + e.getCause());
		}
		return replyMsg;
	}
	
	/**
	 * Get a message from the URL<BR>
	 * default timeout is 5 seconds.
	 * @param url
	 * @param msg
	 * @param params
	 * @return
	 */
	public static String getMsg(String url, String msg, Object... params){
		String replyMsg = null;
		try {
			replyMsg = MicroChatMessager.send(url, "GET", null, params);
		} catch (IOException e) {
			throw new WeiXinException("Get message faild: " + e.getCause());
		}
		return replyMsg;
	}
}
