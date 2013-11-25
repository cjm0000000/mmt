package lemon.shared.media;

/**
 * Media synchronize
 * @author lemon
 * @version 1.1
 *
 */
public class MediaSync {
	private long m_id;
	private String media_id;
	private long created_at;
	private long expire_time;
	
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
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public long getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(long expire_time) {
		this.expire_time = expire_time;
	}
	
}
