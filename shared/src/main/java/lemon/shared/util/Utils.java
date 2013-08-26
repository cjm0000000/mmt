package lemon.shared.util;

import java.util.ArrayList;
import java.util.List;

import lemon.shared.system.bean.CheckTree;
import lemon.shared.system.bean.Menu;
import lemon.shared.system.bean.Tree;
import lemon.shared.system.bean.User;


/**
 * 工具类
 * @author 张连明
 * @date Mar 30, 2012 11:44:47 AM
 */
public class Utils {
	/**
	 * 将菜单转成EXT树所需数据JSON
	 * @param list
	 * @param lev
	 * @return
	 */
	public static List<CheckTree> getMenuTree1(List<Menu> list) {
		List<CheckTree> result = new ArrayList<CheckTree>();
		for (Menu menu : list) {
			if(menu.getMenulevcod().equals("2")){//根
				result.add(new CheckTree(menu.getMenu_code(), menu.getMenu_name(),
						false, true,
						menu.getMenuico(),menu.getAuthority()==null?false:true, new ArrayList<Tree>()));
			}else if(menu.getMenulevcod().equals("3")){
				for (Tree parent : result) {
					if(parent.getId().equals(menu.getSupmenucode())){//添加叶子
						parent.getChildren().add(new CheckTree(menu.getMenu_code(), menu.getMenu_name(),
						true, false, 
						menu.getMenuico(),menu.getAuthority()==null?false:true, null));
					}
				}
			}
		}
		for (Tree tree : result) {
			tree.setExpanded(tree.getChildren().size()!=0);
		}
		return result;
	}
	
	/**
	 * 将菜单转成EXT树所需数据JSON
	 * @param list
	 * @param lev
	 * @return
	 */
	public static List<Tree> getMenuTree2(List<Menu> list,int lev) {
		List<Tree> result = new ArrayList<Tree>();
		for (Menu menu : list) {
			if(menu.getMenulevcod().equals(lev+"")){//根
				result.add(new Tree(menu.getMenu_code(), menu.getMenu_name(),
						false, true, menu.getMenuurl(), menu.getSupmenucode(),
						menu.getMenuico(),menu.getMenulevcod(), new ArrayList<Tree>()));
			}else if(menu.getMenulevcod().equals(lev+1+"")){
				for (Tree parent : result) {
					if(parent.getId().equals(menu.getSupmenucode())){//添加叶子
						parent.getChildren().add(new Tree(menu.getMenu_code(), menu.getMenu_name(),
						true, false, menu.getMenuurl(), menu.getSupmenucode(),
						menu.getMenuico(),menu.getMenulevcod(), null));
					}
				}
			}
		}
		for (Tree tree : result) {
			tree.setExpanded(tree.getChildren().size()!=0);
		}
		return result;
	}
	
	/**
	 * 获取分组ID(带部门)
	 * @param u
	 * @return
	 */
	public static List<String> getGroupIdsByDept(User u){
		List<String> list = new ArrayList<String>();
		list.add("OA1111-"+u.getRole_id()+"-"+u.getDept_code());
		return list;
	}
	
	/**
	 * 获取分组ID(不带部门)
	 * @param u
	 * @return
	 */
	public static List<String> getGroupIds(User u){
		List<String> list = new ArrayList<String>();
		list.add("OA1111-"+u.getRole_id());
		return list;
	}
	
	/**
	 * 获取全部分组
	 * @param u
	 * @return
	 */
	public static List<String> getAllGroupIds(User u){
		List<String> result = getGroupIds(u);
		if(result.addAll(0, getGroupIdsByDept(u)))
			return result;
		else
			return null;
	}
}
