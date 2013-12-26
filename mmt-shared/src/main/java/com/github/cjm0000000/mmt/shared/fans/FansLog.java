package com.github.cjm0000000.mmt.shared.fans;

import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * 粉丝操作日志
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class FansLog extends MmtService {
	private Actions action;
	private String user_id;

	static enum Actions {
		/** 订阅 */
		SUBSCRIBE,
		/** 退订 */
		UNSUBSCRIBE
	}
	
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
