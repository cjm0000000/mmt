package lemon.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
	/** 通用接口URL */
	private static String commonUrl;
	/** 创建菜单URL */
	private static String menuURL_CREATE;
	/** 查询菜单URL */
	private static String menuURL_SEARCH;
	/** 删除菜单URL */
	private static String menuURL_DELETE;

	/**
	 * 清空Map
	 */
	public static void init() {
		if (configs == null)
			configs = new ConcurrentHashMap<>();
		else
			configs.clear();
		try {
			loadWeiXinProperties();
		} catch (IOException e) {
			throw new WeiXinException("Load weixin properties faild. "+ e.getCause());
		}
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
	 * @param params
	 * @return
	 */
	public static String getMsg(String url, Object... params){
		String replyMsg = null;
		try {
			replyMsg = MicroChatMessager.send(url, "GET", null, params);
		} catch (IOException e) {
			throw new WeiXinException("Get message faild: " + e.getCause());
		}
		return replyMsg;
	}
	
	/**
	 * Get common request URL
	 * @return
	 */
	public static String getCommonUrl(){
		return commonUrl;
	}
	
	/**
	 * Get create menu URL
	 * @return
	 */
	public static String getCreateMenuUrl(){
		return menuURL_CREATE;
	}
	
	/**
	 * Get search menu URL
	 * @return
	 */
	public static String getSearchMenuUrl(){
		return menuURL_SEARCH;
	}
	
	/**
	 * Get delete menu URL
	 * @return
	 */
	public static String getDeleteMenuUrl(){
		return menuURL_DELETE;
	}
	
	private static void loadWeiXinProperties() throws IOException{
		InputStream in = null;
		try{
			in = WeiXin.class.getClassLoader().getResourceAsStream("weixin.properties");
			Properties p = new Properties();
			p.load(in);
			commonUrl = p.getProperty("common-url");
			menuURL_CREATE = p.getProperty("menu-create-url");
			menuURL_SEARCH = p.getProperty("menu-search-url");
			menuURL_DELETE = p.getProperty("menu-delete-url");
		}finally{
			if(null != in)
				in.close();
		}
	}
}
