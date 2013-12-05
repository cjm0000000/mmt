package lemon.shared.media;

import lemon.shared.service.BaseService;

/**
 * Media synchronize log
 * @author lemon
 * @version 1.1
 *
 */
public class MediaSyncLog extends BaseService{
	private long media_id;
	private String result;
	
	public long getMedia_id() {
		return media_id;
	}
	public void setMedia_id(long media_id) {
		this.media_id = media_id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
