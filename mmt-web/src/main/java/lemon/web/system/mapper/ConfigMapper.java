package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.Config;


import org.springframework.stereotype.Repository;

/**
 * 系统配置
 * @author 张连明
 * @date May 14, 2012 10:18:34 AM
 */
@Repository
public interface ConfigMapper {
	/**
	 * 获取列表
	 * @param config_name
	 * @return
	 */
	List<Config> list(String config_name);
	
	/**
	 * 获取单个对象
	 * @param config
	 * @return
	 */
	Config load(Config config);
	/**
	 * 添加
	 * @param config
	 */
	void add(Config config);
	/**
	 * 编辑
	 * @param config
	 */
	void edit(Config config);
	/**
	 * 删除
	 * @param config
	 */
	void delete(Config config);
	
	/**
	 * 备份配置
	 */
	void bakConfig();
}
