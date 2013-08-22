package lemon.weixin.bean;

/**
 * Weinxin API return code
 * @author lemon
 *
 */
public class ReturnCode {
	private int retCode;
	private String retDesc;
	
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	public String getRetDesc() {
		return retDesc;
	}
	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}
	
}
