package lemon.web.util;

import java.util.ArrayList;
import java.util.List;

import lemon.web.system.bean.CheckTree;
import lemon.web.system.bean.Menu;
import lemon.web.system.bean.Tree;


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
		/*for (Menu menu : list) {
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
		}*/
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
		/*for (Menu menu : list) {
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
		}*/
		for (Tree tree : result) {
			tree.setExpanded(tree.getChildren().size()!=0);
		}
		return result;
	}
}
