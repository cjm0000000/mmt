package lemon.yixin.message.bean;

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
public class MusicMessage extends MediaMessage {

	private String desc;
	
	public MusicMessage(){
		super(MsgType.MUSIC, "audio/mpeg");
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
