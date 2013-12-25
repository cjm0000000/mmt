package lemon.shared.message.metadata.specific.yixin;

import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.send.MusicMessage;
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
public class YXMusicMessage extends MediaMessage implements MusicMessage {

	private String desc;
	
	public YXMusicMessage(){
		super(MsgType.MUSIC, "audio/mpeg");
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
