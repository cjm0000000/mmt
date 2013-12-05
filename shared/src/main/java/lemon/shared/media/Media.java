package lemon.shared.media;

import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Media Entity
 * @author lemon
 * @version 1.1
 *
 */
public class Media {
	private long id;
	private int cust_id;
	@NotEmpty(message="多媒体类型不能为空")
	private String media_type;
	private int media_size;
	private String real_name;
	private String display_name;
	private String media_path;
	private Date timestamp;
	private Set<MediaSync> syncDetails;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public int getMedia_size() {
		return media_size;
	}
	public void setMedia_size(int media_size) {
		this.media_size = media_size;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getMedia_path() {
		return media_path;
	}
	public void setMedia_path(String media_path) {
		this.media_path = media_path;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Set<MediaSync> getSyncDetails() {
		return syncDetails;
	}
	public void setSyncDetails(Set<MediaSync> syncDetails) {
		this.syncDetails = syncDetails;
	}
	
}
