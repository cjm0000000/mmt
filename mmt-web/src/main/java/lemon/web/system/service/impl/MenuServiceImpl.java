package lemon.web.system.service.impl;

import java.io.File;
import java.util.List;

import lemon.web.system.bean.CheckTree;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Tree;
import lemon.web.system.mapper.MenuMapper;
import lemon.web.system.mapper.RoleMapper;
import lemon.web.system.service.MenuService;
import lemon.web.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 实现Menu业务逻辑
 * @author 张连明
 * @date Mar 30, 2012 11:03:13 AM
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuMapper menuMapper;
	
	public List<Tree> getMenuTreeOnIndex(int role_id) {
		List<Menu> list = menuMapper.getMenuTree(role_id);
		return Utils.getMenuTree2(list,2);
	}

	public List<CheckTree> getAuthorityMenuTree(int role_id) {
		List<Menu> list = roleMapper.getAuthority(role_id);
		return Utils.getMenuTree1(list);
	}
	public boolean reConfigMenuTreeByRoleId(int role_id,File file) {
		if(createMenuFile(file, role_id)){
			//改变状态
			//TODO update role Reload
			//roleMapper.updateReload(role_id);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean createMenuFile(File file, int role_id) {
		// TODO create menu File
		return false;
	}
}
