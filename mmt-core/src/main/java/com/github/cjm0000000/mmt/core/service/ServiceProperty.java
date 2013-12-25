package com.github.cjm0000000.mmt.core.service;

import com.github.cjm0000000.mmt.core.config.MmtConfig;


/**
 * 服务属性接口
 * 
 * @author lemon
 * @version 1.0
 */
public interface ServiceProperty {
	/**
	 * 获取服务类型
	 * 
	 * @return
	 */
	ServiceType getServiceType();
	
	/**
	 * 获取配置信息
	 * @param mmt_token
	 * @return
	 */
	MmtConfig getConfig(String mmt_token);
}
