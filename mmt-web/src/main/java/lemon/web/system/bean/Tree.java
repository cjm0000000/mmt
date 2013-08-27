package lemon.web.system.bean;

import java.util.List;

/**
 * 构造一棵菜单树
 * 
 * @author 张连明
 * @date Mar 17, 2012 11:16:19 PM
 * 
 */
public class Tree {
	/** ID */
	protected String id;
	/** 显示文字 */
	protected String text;
	/** 叶子节点 */
	protected boolean leaf;
	/** 是否展开 */
	protected boolean expanded;
	/** 访问路径 */
	protected String url;
	/** 上级菜单ID */
	protected String supmenucode;
	/** 菜单ico */
	protected String iconCls;
	/** 菜单等级 */
	protected String menulevcod;
	/** 孩子节点 */
	protected List<Tree> children;

	public Tree() {}
	
	public Tree(String id, String text, boolean leaf, boolean expanded,
			String url, String supmenucode, String iconCls, String menulevcod,
			List<Tree> children) {
		super();
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.expanded = expanded;
		this.url = url;
		this.supmenucode = supmenucode;
		this.iconCls = iconCls;
		this.menulevcod = menulevcod;
		this.children = children;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSupmenucode() {
		return supmenucode;
	}

	public void setSupmenucode(String supmenucode) {
		this.supmenucode = supmenucode;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getMenulevcod() {
		return menulevcod;
	}

	public void setMenulevcod(String menulevcod) {
		this.menulevcod = menulevcod;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
}
