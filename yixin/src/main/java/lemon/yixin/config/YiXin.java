package lemon.yixin.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.github.cjm0000000.mmt.yixin.YiXinException;

import lemon.yixin.config.bean.YiXinConfig;

/**
 * YiXin configures
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class YiXin {
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
		if (configs == null){
			configs = new ConcurrentHashMap<>();
			loadYiXinProperties();
		}
		else
			configs.clear();
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
	public static synchronized void setConfig(YiXinConfig config) {
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
	 * 加载易信配置信息
	 */
	private static void loadYiXinProperties() {
		try (InputStream in = YiXin.class.getClassLoader().getResourceAsStream(
				"yixin.properties")) {
			Properties p = new Properties();
			p.load(in);
			commonUrl = p.getProperty("common-url");
			menuURL_CREATE = p.getProperty("menu-create-url");
			menuURL_SEARCH = p.getProperty("menu-search-url");
			menuURL_DELETE = p.getProperty("menu-delete-url");
		} catch (IOException e) {
			throw new YiXinException("Load YiXin properties faild: " + e.getCause());
		}
	}
}
