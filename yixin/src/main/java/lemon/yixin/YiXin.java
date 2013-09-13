package lemon.yixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.biz.YiXinException;
import lemon.yixin.gateway.YiXinMessager;

/**
 * YiXin configures
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class YiXin {
	/** This is local encoding */
	public static final String LOCAL_CHARSET = "UTF-8";
	/** This is remote encoding */
	public static final String TARGET_CHARSET = "UTF-8";
	private static ConcurrentMap<String, YiXinConfig> configs;
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
	public synchronized static void init() {
		if (configs == null)
			configs = new ConcurrentHashMap<>();
		else
			configs.clear();
		try {
			loadYiXinProperties();
		} catch (IOException e) {
			throw new YiXinException("Load YiXin properties faild. "+ e.getCause());
		}
	}

	/**
	 * 获取YiXin通用配置
	 * @param mmt_token
	 * @return
	 */
	public static YiXinConfig getConfig(String mmt_token) {
		return configs.get(mmt_token);
	}

	/**
	 * 设置YiXin通用配置
	 * @param config
	 */
	public static void setConfig(YiXinConfig config) {
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
			replyMsg = YiXinMessager.send(url, "POST", msg, params);
		} catch (IOException e) {
			throw new YiXinException("POST message faild: " + e.getCause());
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
			replyMsg = YiXinMessager.send(url, "GET", null, params);
		} catch (IOException e) {
			throw new YiXinException("Get message faild: " + e.getCause());
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
	
	private static void loadYiXinProperties() throws IOException{
		InputStream in = null;
		try{
			in = YiXin.class.getClassLoader().getResourceAsStream("yixin.properties");
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
