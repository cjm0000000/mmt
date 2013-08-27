package lemon.web.system.mapper;

import java.util.List;
import java.util.Map;

import lemon.web.system.bean.Menu;

import org.springframework.stereotype.Repository;


/**
 * 读取系统菜单
 * 
 * @author 张连明
 * @date Mar 17, 2012 3:02:09 PM
 * 
 */
@Repository
public interface MenuMapper {
	/**
	 * 显示全部菜单
	 * 
	 * @return
	 */
	List<Menu> listAllMenu();

	/**
	 * 获取菜单树
	 * 
	 * @param role_id
	 * @return
	 */
	List<Menu> getMenuTree(int role_id);

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 */
	void add(Menu menu);

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 */
	void edit(Menu menu);

	/**
	 * 删除菜单
	 * 
	 * @param menu_code
	 */
	void delMenu(String menu_code);
	
	/**
	 * 获取非叶子节点
	 * @return
	 */
	List<Menu> getParentMenus();
	
	/**
	 * 生成菜单编号
	 * @return
	 */
	String createMenuCode(Map<String, String> map);
	
	/**
	 * 获取单个菜单
	 * @param menu_code
	 * @return
	 */
	Menu loadMenu(String menu_code);
	
	/**
	 * 改变权限加载状态
	 * @param menu_code
	 */
	void reloadMenuFlag(String menu_code);
}
