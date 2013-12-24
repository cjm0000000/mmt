package lemon.shared.fans;

import com.github.cjm0000000.mmt.core.config.Status;

import lemon.shared.service.BaseService;

/**
 * Bean for fans
 * @author lemon
 * @version 1.0
 */
public class Fans extends BaseService {
	private String user_id;
	private String nick_name;
	private Status status;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
