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

	/**
	 * 清空Map
	 */
	public static void init() {
		if (configs == null)
			configs = new ConcurrentHashMap<>();
		else
			configs.clear();
	}

	/**
	 * 获取WeiXin通用配置
	 * @param mmt_token
	 * @return
	 */
	public static WeiXinConfig getConfig(String mmt_token) {
		return configs.get(mmt_token);
	}

	/**
	 * 设置WeiXin通用配置
	 * @param config
	 */
	public static void setConfig(WeiXinConfig config) {
		configs.put(config.getApi_url(), config);
	}
	
	/**
	 * 销毁Map
	 */
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
