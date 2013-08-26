package lemon.shared.system.bean;

import java.util.List;

/**
 * 带复选框的树
 * @author 张连明
 * @date Mar 29, 2012 10:50:47 PM
 *
 */
public class CheckTree extends Tree {
	/** 菜单是否选中 */
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public CheckTree(){}
	public CheckTree(String id, String text, boolean leaf, boolean expanded,
			String iconCls, boolean checked, List<Tree> children) {
		super();
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.expanded = expanded;
		this.iconCls = iconCls;
		this.checked = checked;
		this.children = children;
	}
}
