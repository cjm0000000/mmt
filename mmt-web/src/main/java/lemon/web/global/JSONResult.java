package lemon.web.global;

/**
 * JSON result bean
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class JSONResult {
	private boolean success;
	private String msg;

	public JSONResult(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
