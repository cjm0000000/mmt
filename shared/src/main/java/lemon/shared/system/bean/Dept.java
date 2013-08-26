package lemon.shared.system.bean;

/**
 * 部门Model
 * 
 * @author 张连明
 * @date Mar 20, 2012 1:16:27 PM
 */
public class Dept {
	private String dept_code;
	private String dept_name;
	private String deptlevcod;
	private String supdeptcod;
	private int sort;

	public Dept() {
	}
	public Dept(String dept_code) {
		this.dept_code = dept_code;
	}
	public Dept(String dept_code, String dept_name, String deptlevcod,
			String supdeptcod, int sort) {
		super();
		this.dept_code = dept_code;
		this.dept_name = dept_name;
		this.deptlevcod = deptlevcod;
		this.supdeptcod = supdeptcod;
		this.sort = sort;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDeptlevcod() {
		return deptlevcod;
	}

	public void setDeptlevcod(String deptlevcod) {
		this.deptlevcod = deptlevcod;
	}

	public String getSupdeptcod() {
		return supdeptcod;
	}

	public void setSupdeptcod(String supdeptcod) {
		this.supdeptcod = supdeptcod;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
