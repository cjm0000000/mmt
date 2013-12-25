package lemon.shared.message.metadata.specific.weixin;

import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.send.MusicMessage;
import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Music message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class WXMusicMessage extends Message implements MusicMessage {
	/** MusicUrl */
	@XStreamAlias("MusicUrl")
	@XStreamCDATA
	private String musicUrl;
	/** HQMusicUrl */
	@XStreamAlias("HQMusicUrl")
	@XStreamCDATA
	private String hqMusicUrl;
	
	public WXMusicMessage(){
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
