package lemon.web.system.service;

import java.io.File;
import java.util.List;

import lemon.web.system.bean.CheckTree;
import lemon.web.system.bean.Tree;

/**
 * 处理菜单相关业务
 * @author 张连明
 * @date Mar 30, 2012 10:57:53 AM
 */
public interface MenuService {
	/**
	 * 主页菜单树
	 * @param role_id
	 * @return
	 */
	List<Tree> getMenuTreeOnIndex(int role_id);
	/**
	 * 获取权限菜单（带权限标识）
	 * @param role_id
	 * @return
	 */
	List<CheckTree> getAuthorityMenuTree(int role_id);
	/**
	 * 重新配置权限树
	 * @param role_id
	 * @param file
	 * @return
	 */
	boolean reConfigMenuTreeByRoleId(int role_id,File file);
	
	/**
	 * 生成文件
	 * @param file
	 * @param role_id
	 * @return
	 */
	boolean createMenuFile(File file,int role_id);
}
