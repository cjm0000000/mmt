package lemon.shared.fans.log;

import lemon.shared.fans.Actions;
import lemon.shared.service.BaseService;

/**
 * 粉丝操作日志
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class FansLog extends BaseService {
	private Actions action;
	private String user_id;

	public Actions getAction() {
		return action;
	}
	public void setAction(Actions action) {
		this.action = action;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
