package com.github.cjm0000000.mmt.shared.media;

import com.github.cjm0000000.mmt.core.service.MmtService;

/**
 * Media synchronize
 * @author lemon
 * @version 1.1
 *
 */
public class MediaSync extends MmtService {
	private long m_id;
	private String media_id;
	private int created_at;
	private int expire_time;
	
	public long getM_id() {
		return m_id;
	}
	public void setM_id(long m_id) {
		this.m_id = m_id;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public int getCreated_at() {
		return created_at;
	}
	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}
	public int getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}
	
}
