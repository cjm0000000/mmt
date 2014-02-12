package com.github.cjm0000000.mmt.web.ui;

/**
 * Bootstrap 3 UI toolkit
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class BS3UI {
	private static final String SUCCESS = "alert alert-success";
	private static final String INFO 	= "alert alert-info";
	private static final String WARNING = "alert alert-warning";
	private static final String DANGER 	= "alert alert-danger";

	/**
	 * 成功信息
	 * @param msg
	 * @return
	 */
	public static String success(String msg) {
		return template(SUCCESS, msg);
	}

	/**
	 * 提示信息
	 * @param msg
	 * @return
	 */
	public static String info(String msg) {
		return template(INFO, msg);
	}

	/**
	 * 警告
	 * @param msg
	 * @return
	 */
	public static String warning(String msg) {
		return template(WARNING, msg);
	}

	/**
	 * 错误
	 * @param msg
	 * @return
	 */
	public static String danger(String msg) {
		return template(DANGER, msg);
	};

	private static String template(String type, String content) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"json-result ").append(type).append(" alert-dismissable").append("\">");
		sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
		sb.append(content);
		sb.append("</div>");
		return sb.toString();
	}
}
