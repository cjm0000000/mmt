package com.github.cjm0000000.mmt.core.file;

/**
 * File manager<br>
 * 提供文件读写服务的统一接口
 * @author lemon
 * @version 1.1
 *
 */
public interface FileManager {
	/**
	 * 写文件到文件系统
	 * @param path
	 * @param fileName
	 * @param bytes
	 */
	void writeFile(String path, String fileName, byte[] bytes);
	
	/**
	 * 从文件系统读取文件
	 * @param path
	 * @param fineName
	 * @return
	 */
	byte[] readFile(String path, String fileName);
}
