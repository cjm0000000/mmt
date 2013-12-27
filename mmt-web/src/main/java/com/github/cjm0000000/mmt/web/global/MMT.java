package com.github.cjm0000000.mmt.web.global;

/**
 * 存放全局变量信息
 * @author lemon
 * @version 1.0
 *
 */
public final class MMT {
	static String contextRoot;
	
	static String uploadFileRoot;
	/** Spring MVC 过滤器根路径 */
	public static final String FILTER_ROOT = "/webservices/";
	
	static synchronized void setContextRoot(String root){
		contextRoot = root;
	}
	
	static synchronized void setUploadFileRoot(String phyPath){
		uploadFileRoot = phyPath;
	}
	
	/**
	 * 获取容器根目录
	 * @return
	 */
	public static String getContextRoot(){
		return contextRoot;
	}
	
	/**
	 * 获取文件上传根目录
	 * @return
	 */
	public static String getUploadFileRoot(){
		return uploadFileRoot;
	}
}
