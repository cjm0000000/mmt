package lemon.shared.log.bean;

import lemon.shared.entity.Action;
import lemon.shared.service.BaseService;

/**
 * custom menu log
 * @author lemon
 * @version 1.0
 *
 */
public class CustomMenuLog extends BaseService {
	private Action action;
	private String access_token;
	private String msg;
	private String result;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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
