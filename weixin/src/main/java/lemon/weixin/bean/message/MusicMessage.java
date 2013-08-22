package lemon.weixin.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Music message
 * @author lemon
 *
 */
public class MusicMessage extends BasicMessage {
	/** MusicUrl */
	@XStreamAlias("MusicUrl")
	private String musicUrl;
	/** HQMusicUrl */
	@XStreamAlias("HQMusicUrl")
	private String hqMusicUrl;
	
	public MusicMessage(){
		super(MsgType.MUSIC);
	}
	
	public String getMusicUrl() {
		return musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
}
