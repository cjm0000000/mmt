package lemon.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.biz.WeiXinException;

/**
 * WeiXin configures
 * 
 * @author lemon
 * @version 1.0
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
	public synchronized static void init() {
		if (configs == null){
			configs = new ConcurrentHashMap<>();
			loadWeiXinProperties();
		}
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
	public synchronized static void setConfig(WeiXinConfig config) {
		configs.put(config.getApi_url(), config);
	}
	
	/**
	 * 销毁Map
	 */
	public synchronized static void destory(){
		if(null != configs)
			configs.clear();
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
	
	/**
	 * load WeiXin's configures
	 */
	private static void loadWeiXinProperties() {
		try (InputStream in = WeiXin.class.getClassLoader()
				.getResourceAsStream("weixin.properties")) {
			Properties p = new Properties();
			p.load(in);
			commonUrl = p.getProperty("common-url");
			menuURL_CREATE = p.getProperty("menu-create-url");
			menuURL_SEARCH = p.getProperty("menu-search-url");
			menuURL_DELETE = p.getProperty("menu-delete-url");
		} catch (IOException e) {
			throw new WeiXinException("Load weixin properties faild: "+ e.getCause());
		}
	}
}
