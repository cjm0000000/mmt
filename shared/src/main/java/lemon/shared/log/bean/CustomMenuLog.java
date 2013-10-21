package lemon.shared.log.bean;

import lemon.shared.customer.bean.BaseService;
import lemon.shared.entity.Action;

/**
 * custom menu log
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenuLog extends BaseService {
	private Action action;
	private String accesstoken;
	private String msg;
	private String result;
	
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
}
