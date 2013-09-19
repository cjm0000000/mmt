package lemon.web.global;

/**
 * 存放全局变量信息
 * @author lemon
 * @version 1.0
 *
 */
public final class MMT {
	static String  contextRoot;
	/** Spring MVC 过滤器根路径 */
	public static final String FILTER_ROOT = "/webservices/";
	
	static void setContextRoot(String root){
		contextRoot = root;
	}
	
	/**
	 * 获取容器根目录
	 * @return
	 */
	public static String getContextRoot(){
		return contextRoot;
	}
}
