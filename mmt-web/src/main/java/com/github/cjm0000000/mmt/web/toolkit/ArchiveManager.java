package com.github.cjm0000000.mmt.web.toolkit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 归档管理器
 * 
 * @author lemon
 * @version 1.1
 * 
 */
public class ArchiveManager {
	private static final String PUBLIC_PATH = "public" + File.separator;

	/**
	 * 获取私有归档路径[相对路径]
	 * 
	 * @param cust_id
	 * @return
	 */
	public static String getPrivateFilePath(int cust_id) {
		return cust_id + File.separator + getDefaultFilePath();
	}

	/**
	 * 获取公共归档路径[相对路径]
	 * 
	 * @return
	 */
	public static String getPublicFilePath() {
		return PUBLIC_PATH + getDefaultFilePath();
	}

	/**
	 * 默认归档路径[相对路径]
	 * @return
	 */
	private static String getDefaultFilePath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM");
		String[] ym = sdf.format(new Date()).split(",");
		final StringBuilder sb = new StringBuilder();
		sb.append(ym[0]).append(File.separator);
		sb.append(ym[1]).append(File.separator);
		return sb.toString();
	}
	
}
