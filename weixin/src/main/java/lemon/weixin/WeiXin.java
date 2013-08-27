package lemon.weixin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lemon.weixin.bean.WeiXinConfig;

/**
 * WeiXin configs
 * 
 * @author lemon
 * 
 */
public class WeiXin {
	private static ConcurrentMap<Integer, Object> configs = new ConcurrentHashMap<>();

	/**
	 * 获取WeiXin通用配置
	 * 
	 * @param cust_id
	 * @return
	 */
	public static WeiXinConfig getConfig(int cust_id) {
		return (WeiXinConfig) configs.get(cust_id);
	}

	/**
	 * 更改WeiXin通用配置
	 * @param cust_id
	 * @param config
	 */
	public static void setConfig(int cust_id, WeiXinConfig config) {
		configs.put(cust_id, config);
	}
}
