package com.github.cjm0000000.mmt.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;

/**
 * WeiXin configures
 * 
 * @author lemon
 * @version 1.0
 * 
 */
//TODO 抽取各种URL到独立class
public class WeiXin {
	private static ConcurrentMap<String, WeiXinConfig> configs;
	/** 通用接口URL */
	private static String COMMON_URL;
	/** 创建菜单URL */
	private static String MENU_CREATE_URL;
	/** 查询菜单URL */
	private static String MENU_SEARCH_URL;
	/** 删除菜单URL */
	private static String MENU_DELETE_URL;
	/** 上传多媒体文件URL */
	private static String MEDIA_UPLOAD_URL;
	/** 下载多媒体文件URL */
	private static String MEDIA_DOWNLOAD_URL;
	/** 发送客服消息的URL */
	private static String CUSTOM_MSG_URL;
	
	static{
		loadWeiXinProperties();
	}

	/**
	 * 清空Map
	 */
	public synchronized static void init() {
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
		return COMMON_URL;
	}
	
	/**
	 * Get create menu URL
	 * @return
	 */
	public static String getCreateMenuUrl(){
		return MENU_CREATE_URL;
	}
	
	/**
	 * Get search menu URL
	 * @return
	 */
	public static String getSearchMenuUrl(){
		return MENU_SEARCH_URL;
	}
	
	/**
	 * Get delete menu URL
	 * @return
	 */
	public static String getDeleteMenuUrl(){
		return MENU_DELETE_URL;
	}
	
	/**
	 * Get upload media URL
	 * @return
	 */
	public static String getUploadMediaUrl(){
		return MEDIA_UPLOAD_URL;
	}
	
	/**
	 * Get download media URL
	 * @return
	 */
	public static String getDownloadMediaUrl(){
		return MEDIA_DOWNLOAD_URL;
	}
	
	public static String getCustomMsgUrl(){
		return CUSTOM_MSG_URL;
	}
	
	/**
	 * load WeiXin's configures
	 */
	private static void loadWeiXinProperties() {
		try (InputStream in = WeiXin.class.getClassLoader().getResourceAsStream("weixin.properties")) {
			Properties p = new Properties();
			p.load(in);
			COMMON_URL = p.getProperty("common-url");
			MENU_CREATE_URL = p.getProperty("menu-create-url");
			MENU_SEARCH_URL = p.getProperty("menu-search-url");
			MENU_DELETE_URL = p.getProperty("menu-delete-url");
			MEDIA_UPLOAD_URL = p.getProperty("media-upload-url");
			MEDIA_DOWNLOAD_URL = p.getProperty("media-download-url");
			CUSTOM_MSG_URL = p.getProperty("custom-msg-url");
		} catch (IOException e) {
			throw new WeiXinException("Load weixin properties faild: "+ e.getCause());
		}
	}
}
