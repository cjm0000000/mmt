package com.github.cjm0000000.mmt.shared.fans;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * Bean for fans
 * @author lemon
 * @version 1.0
 */
public class Fans extends MmtService implements Cloneable {
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
	
	public Fans clone(){
		try {
			return (Fans) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new MmtException("Can't clone Fans.", e.getCause());
		}
	}
}
