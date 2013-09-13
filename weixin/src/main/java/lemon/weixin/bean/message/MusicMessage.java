package lemon.weixin.bean.message;

import lemon.shared.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Music message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
public class MusicMessage extends WeiXinMessage {
	/** MusicUrl */
	@XStreamAlias("MusicUrl")
	@XStreamCDATA
	private String musicUrl;
	/** HQMusicUrl */
	@XStreamAlias("HQMusicUrl")
	@XStreamCDATA
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
